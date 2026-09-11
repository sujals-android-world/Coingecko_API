package com.example.coingeckoapi.domain.usacases.allUserUseCases

import com.example.coingeckoapi.domain.model.UserModel
import com.example.coingeckoapi.domain.repository.PreferenceRepository
import com.example.coingeckoapi.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.getUser()
}