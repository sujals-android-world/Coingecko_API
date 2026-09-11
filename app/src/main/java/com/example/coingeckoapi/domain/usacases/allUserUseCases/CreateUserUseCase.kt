package com.example.coingeckoapi.domain.usacases.allUserUseCases

import com.example.coingeckoapi.domain.model.UserModel
import com.example.coingeckoapi.domain.repository.UserRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userModel: UserModel) : Result<Unit> {
        if(userModel.amount <= 0) {
            return Result.failure(Exception("Amount must be greater than zero"))
        }
        if(userModel.name.isBlank()) {
            return Result.failure(Exception("Name cannot be empty"))
        }
        if (userModel.name.length < 3) {
            return Result.failure(Exception("Name must be at least 3 characters long"))
        }
        if (userModel.name.length > 10) {
            return Result.failure(Exception("Name must be at most 10 characters long"))
        }
        if (userModel.email.isBlank()) {
            return Result.failure(Exception("Email cannot be empty"))
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userModel.email).matches()) {
            return Result.failure(Exception("Invalid email format"))
        }
        if (userModel.password.isBlank()) {
            return Result.failure(Exception("Password cannot be empty"))
        }
        if (userModel.password.length < 6) {
            return Result.failure(Exception("Password must be at least 6 characters long"))
        }
        if (userModel.password.length > 12) {
            return Result.failure(Exception("Password must be at most 12 characters long"))
        }
        return try {
            repository.createUser(userModel)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}