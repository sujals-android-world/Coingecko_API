package com.example.coingeckoapi.domain.repository

import com.example.coingeckoapi.domain.model.CoinModel
import kotlinx.coroutines.flow.Flow


interface CoinRepository {

     fun getCoinList(ids: String?) : Flow<List<CoinModel>>


     suspend fun refreshCoins() : Result<Unit>


}