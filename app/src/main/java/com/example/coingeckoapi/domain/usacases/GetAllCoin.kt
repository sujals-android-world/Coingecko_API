package com.example.coingeckoapi.domain.usacases

import com.example.coingeckoapi.domain.dto.CoinModel
import com.example.coingeckoapi.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllCoin @Inject constructor(
    private val repository: CoinRepository
) {

     operator fun invoke(ids: String? = null) : Flow<List<CoinModel>> {
        return repository.getCoinList(ids)
    }

}