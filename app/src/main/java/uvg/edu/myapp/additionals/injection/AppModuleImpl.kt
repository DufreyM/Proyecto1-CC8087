package uvg.edu.myapp.additionals.injection

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import uvg.edu.myapp.authentication.data.AuthRepositoryImpl
import uvg.edu.myapp.authentication.domain.AuthRepository

class AppModuleImpl(
    private val appContext: Context
): AppModule {
    override val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(firebaseAuth)
    }
}