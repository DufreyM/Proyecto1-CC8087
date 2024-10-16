package com.example.waterreminder.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterreminder.Injection
import com.example.waterreminder.data.UserRepository
import com.google.firebase.auth.FirebaseAuth

import kotlinx.coroutines.launch
class AuthViewModel : ViewModel() {
    private val userRepository: UserRepository
    init {
        userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
    }
    private val _authResult = MutableLiveData<com.example.waterreminder.data.Result<Boolean>>()
    val authResult: LiveData<com.example.waterreminder.data.Result<Boolean>> get() = _authResult
    fun signUp(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            _authResult.value = userRepository.signUp(email, password, firstName, lastName)
        }
    }
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authResult.value = userRepository.login(email, password)
        }
    }
}