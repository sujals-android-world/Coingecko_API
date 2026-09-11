package com.example.coingeckoapi.data.repositoryImpl

import com.example.coingeckoapi.domain.model.CoinModel
import com.example.coingeckoapi.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CoinFakeRepository : CoinRepository {


    var listCoinModel : List<CoinModel> = emptyList()

    override fun getCoinList(ids: String?): Flow<List<CoinModel>> {
        return flowOf(listCoinModel)
    }

    override suspend fun refreshCoins(): Result<Unit> {
        TODO("Not yet implemented")
    }
}