package com.example.coingeckoapi.domain.usacases.allUserUseCases

import com.example.coingeckoapi.domain.repository.PreferenceRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(user: Boolean) {
        repository.saveUser(user)
    }
}