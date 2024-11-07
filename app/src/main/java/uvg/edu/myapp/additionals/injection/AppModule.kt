package uvg.edu.myapp.additionals.injection

import com.google.firebase.auth.FirebaseAuth
import uvg.edu.myapp.authentication.domain.AuthRepository

interface AppModule {
    val firebaseAuth: FirebaseAuth
    val authRepository: AuthRepository
}