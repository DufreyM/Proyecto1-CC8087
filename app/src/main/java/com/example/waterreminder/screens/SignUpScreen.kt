package com.example.waterreminder.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// Clase User personalizada
data class User(
    val id: String = "",
    val name: String = "",
    val email: String = ""
)

@Composable
fun SignUpScreen(navController: NavController, auth: FirebaseAuth, firestore: FirebaseFirestore) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") } // Campo para el nombre
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign Up")

        // Campo de texto para el nombre
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        // Campo de texto para el email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        // Campo de texto para la contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        // Campo de texto para confirmar la contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        if (error.isNotEmpty()) {
            Text(text = error, color = Color.Red)
        }

        Button(onClick = {
            if (password == confirmPassword) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Usuario creado exitosamente en Firebase Authentication
                            val userId = task.result?.user?.uid ?: ""

                            // Guardar datos del usuario en Firestore
                            val user = User(id = userId, name = name, email = email)
                            firestore.collection("users").document(userId).set(user)
                                .addOnSuccessListener {
                                    // Navegar a la pantalla principal después de guardar el usuario
                                    navController.navigate("Home")
                                }
                                .addOnFailureListener { e ->
                                    error = "Error al guardar usuario en Firestore: $e"
                                }
                        } else {
                            // Error al crear el usuario
                            error = "Error: ${task.exception?.message}"
                        }
                    }
            } else {
                error = "Passwords do not match"
            }
        }) {
            Text("Sign Up")
        }
    }
}
