package uvg.edu.myapp.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uvg.edu.myapp.authentication.domain.AuthRepository

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    private val _authViewState = MutableStateFlow(AuthViewState())
    val authViewState = _authViewState.asStateFlow()

    init {
        checkUserAuthentication()
    }

    private fun checkUserAuthentication() {
        viewModelScope.launch {
            if (authRepository.isUserAuthenticated()) {
                _authState.value = AuthState.Authenticated
            }
        }
    }

    fun registerUser(email: String, password: String) {
        if(email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error(message = "ERROR: Campos vacíos")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.registerUser(email, password)
            _authState.value = if(result.isSuccess) AuthState.Authenticated else AuthState.Error(
                message = result.exceptionOrNull()?.localizedMessage
                ?: "Ocurrió un ERROR"
            )
        }
    }

    fun loginUser(email: String, password: String) {
        if(email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error(message = "ERROR: Campos vacíos")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.loginUser(email, password)
            _authState.value = if(result.isSuccess) AuthState.Authenticated else AuthState.Error(
                message = result.exceptionOrNull()?.localizedMessage
                    ?: "Ocurrió un ERROR"
            )
        }
    }

    fun logoutUser() {
        authRepository.logoutUser()
        _authState.value = AuthState.Idle
    }

    fun setNewViewValue(name: String = "", email: String = "", password: String = "") {
        _authViewState.value = _authViewState.value.copy(
            nombre = name,
            email = email,
            password = password
        )
    }
}