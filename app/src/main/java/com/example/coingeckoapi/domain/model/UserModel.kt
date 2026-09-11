package com.example.coingeckoapi.domain.model

data class UserModel(
    val id: Int = 0,
    val name: String,
    val email: String,
    val password : String,
    val amount : Double
)