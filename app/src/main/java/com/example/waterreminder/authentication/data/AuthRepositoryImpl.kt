package com.example.waterreminder.authentication.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import com.example.waterreminder.authentication.domain.AuthRepository
import com.example.waterreminder.authentication.domain.Usuario
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
): AuthRepository {
    override suspend fun isUserAuthenticated(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun registerUser(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("ERROR-AuthRepo", "Error al registrar usuario")
            Result.failure(e)
        }
    }

    override suspend fun loginUser(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("ERROR-AuthRepo", "Error al iniciar sesión")
            Result.failure(e)
        }
    }

    override fun logoutUser() {
        firebaseAuth.signOut()
    }

    override suspend fun saveUserInfo(usuario: Usuario): Result<Unit> {
        return try {
            val userId = firebaseAuth.currentUser?.uid
            if (userId != null) {
                firebaseFirestore.collection("usuarios").document(userId).set(usuario).await()
                Result.success(Unit)
            } else {
                Log.d("ERROR-AuthRepo", "Error al obtener la sesión del usuario")
                Result.failure(Exception("Usuario no autenticado"))
            }
        } catch (e: Exception) {
            Log.d("ERROR-AuthRepo", "Error al guardar información del usuario")
            Result.failure(e)
        }
    }

    override suspend fun getUserInfo(): Result<Usuario?> {
        return try {
            val userId = firebaseAuth.currentUser?.uid
            if (userId != null) {
                val document = firebaseFirestore.collection("usuarios").document(userId).get().await()
                if (document.exists()) {
                    val usuario = document.toObject(Usuario::class.java)
                    Result.success(usuario)
                } else {
                    Result.success(null)
                }
            } else {
                Log.d("ERROR-AuthRepo", "Error al obtener la sesión del usuario")
                Result.failure(Exception("Usuario no autenticado"))
            }
        } catch (e: Exception) {
            Log.d("ERROR-AuthRepo", "Error al obtener información del usuario")
            Result.failure(e)
        }
    }
}