package com.example.waterreminder.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.waterreminder.authentication.domain.AuthRepository
import com.example.waterreminder.authentication.domain.Usuario

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    private val _userViewState = MutableStateFlow(UserViewState())
    val userViewState = _userViewState.asStateFlow()

    private val _profileViewState = MutableStateFlow(Usuario())
    val profileViewState = _profileViewState.asStateFlow()

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

    fun registerUser(name: String, email: String, password: String, gender: String) {
        if(email.isEmpty() || password.isEmpty() || name.isEmpty() || gender == "Sin específicar") {
            _authState.value = AuthState.Error(message = "ERROR: Campos vacíos")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.registerUser(email, password)
            val result2 = authRepository.saveUserInfo(Usuario(username = name, gender = gender))
            _authState.value = if(result.isSuccess && result2.isSuccess) AuthState.Authenticated else AuthState.Error(
                message = if(result.isSuccess) result2.exceptionOrNull()?.localizedMessage ?: "Ocurrió un ERROR"
                else result.exceptionOrNull()?.localizedMessage
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

    fun logOutUser() {
        authRepository.logoutUser()
    }

    fun setNewViewValue(
        name: String? = null,
        gender: String? = null,
        email: String? = null,
        password: String? = null
    ) {
        if(name != null) {
            _userViewState.value = _userViewState.value.copy(
                nombre = name
            )
        }
        if(gender != null) {
            _userViewState.value = _userViewState.value.copy(
                gender = gender
            )
        }
        if(email != null) {
            _userViewState.value = _userViewState.value.copy(
                email = email
            )
        }
        if(password != null) {
            _userViewState.value = _userViewState.value.copy(
                password = password
            )
        }
        _userViewState.value = _userViewState.value.copy(
            errorMessage = null
        )
    }

    fun dropGenderMenu(
        drop: Boolean
    ) {
        _userViewState.value = _userViewState.value.copy(
            dropGenderSelector = drop
        )
    }

    fun showDataWindow(
        show: Boolean
    ) {
        _userViewState.value = _userViewState.value.copy(
            seeUserData = show
        )
    }

    fun resetAuthState() {
        _userViewState.value = _userViewState.value.copy(
            errorMessage = (_authState.value as AuthState.Error).message
        )
        _authState.value = AuthState.Idle
    }

    fun getUserInfo() {
        viewModelScope.launch {
            _profileViewState.value =
                authRepository.getUserInfo().getOrNull() ?: _profileViewState.value
        }
    }
}