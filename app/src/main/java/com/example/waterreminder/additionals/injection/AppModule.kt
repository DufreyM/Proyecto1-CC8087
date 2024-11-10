package com.example.waterreminder.additionals.injection

import com.google.firebase.auth.FirebaseAuth
import com.example.waterreminder.authentication.domain.AuthRepository
import com.google.firebase.firestore.FirebaseFirestore

interface AppModule {
    val firebaseAuth: FirebaseAuth
    val firebaseFirestore: FirebaseFirestore
    val authRepository: AuthRepository
}