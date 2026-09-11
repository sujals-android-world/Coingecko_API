package com.example.coingeckoapi.domain.usacases.allUserUseCases

data class AllUserUseCases (
    val getUserUseCase: GetUserUseCase,
    val saveUserUseCase: SaveUserUseCase,
    val createUserUseCase: CreateUserUseCase,
    val getUserDetailUseCase: GetUserDetailUseCase
)