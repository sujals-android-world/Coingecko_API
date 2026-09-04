package com.example.coingeckoapi.domain.repository

import com.example.coingeckoapi.domain.dto.CoinModel
import kotlinx.coroutines.flow.Flow


interface CoinRepository {

     fun getCoinList(ids: String?) : Flow<List<CoinModel>>


     suspend fun refreshCoins() : Result<Unit>


}