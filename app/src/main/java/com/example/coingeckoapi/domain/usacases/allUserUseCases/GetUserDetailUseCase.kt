package com.example.coingeckoapi.domain.usacases.allUserUseCases

import com.example.coingeckoapi.domain.model.UserModel
import com.example.coingeckoapi.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke() : Flow<UserModel?> {
        return repository.getUserDetail()
    }
}