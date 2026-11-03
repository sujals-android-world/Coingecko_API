package com.example.coingeckoapi.domain.usacases.allUserUseCases

import com.example.coingeckoapi.domain.model.UserModel
import com.example.coingeckoapi.domain.repository.UserRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userModel: UserModel): Result<Unit> {
        val validationError = validate(userModel)
        if (validationError != null) {
            return Result.failure(Exception(validationError))
        }

        return try {
            repository.createUser(userModel)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun validate(userModel: UserModel): String? {
        return when {
            userModel.amount <= 0 -> "Amount must be greater than zero"
            userModel.name.isBlank() -> "Name cannot be empty"
            userModel.name.length < 3 -> "Name must be at least 3 characters long"
            userModel.name.length > 10 -> "Name must be at most 10 characters long"
            userModel.email.isBlank() -> "Email cannot be empty"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(userModel.email).matches() -> "Invalid email format"
            userModel.password.isBlank() -> "Password cannot be empty"
            userModel.password.length < 6 -> "Password must be at least 6 characters long"
            userModel.password.length > 12 -> "Password must be at most 12 characters long"
            else -> null
        }
    }
}
