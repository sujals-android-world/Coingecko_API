package com.example.coingeckoapi.domain.usacases

import com.example.coingeckoapi.domain.dto.CoinModel
import com.example.coingeckoapi.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinByIdUseCase @Inject constructor(
    private val repository: CoinRepository
) {

//    suspend operator fun invoke(id: String): CoinModel? = repository.getCoinById(id)

}