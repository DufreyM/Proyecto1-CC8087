package uvg.edu.myapp.authentication.presentation

sealed class AuthState {
    data object Idle: AuthState()
    data object Authenticated: AuthState()
    data object Loading: AuthState()
    data class Error(val message: String) : AuthState()
}