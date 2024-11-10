package com.example.waterreminder.authentication.domain

data class Usuario(
    val username: String = "",
    val gender: String = "Sin específicar",
    val level: Int = 1,
    val exp: Int = 0,
    val coins: Int = 0
)