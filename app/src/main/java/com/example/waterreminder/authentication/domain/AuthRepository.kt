package com.example.waterreminder.authentication.domain

interface AuthRepository {
    suspend fun isUserAuthenticated(): Boolean
    suspend fun registerUser(email: String, password: String): Result<Unit>
    suspend fun loginUser(email: String, password: String): Result<Unit>
    fun logoutUser()
    suspend fun saveUserInfo(usuario: Usuario): Result<Unit>
    suspend fun getUserInfo(): Result<Usuario?>
}