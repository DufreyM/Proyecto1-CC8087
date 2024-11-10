package com.example.waterreminder.authentication.presentation

data class UserViewState(
    val nombre: String = "",
    val gender: String = "Sin específicar",
    val email: String = "",
    val password: String = "",
    val errorMessage: String? = null,
    val seeUserData: Boolean = false,
    val dropGenderSelector: Boolean = false
)