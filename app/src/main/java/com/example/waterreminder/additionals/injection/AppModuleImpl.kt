package com.example.waterreminder.additionals.injection

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.example.waterreminder.authentication.data.AuthRepositoryImpl
import com.example.waterreminder.authentication.domain.AuthRepository
import com.google.firebase.firestore.FirebaseFirestore

class AppModuleImpl(
    private val appContext: Context
): AppModule {
    override val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(firebaseAuth, firebaseFirestore)
    }
}