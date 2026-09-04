package com.example.coingeckoapi.domain.usacases

import com.example.coingeckoapi.domain.repository.CoinRepository
import javax.inject.Inject

class RefreshCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke() : Result<Unit> {
       return repository.refreshCoins()
    }
}