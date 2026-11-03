package com.example.coingeckoapi.data.repositoryImpl

import com.example.coingeckoapi.data.coinMapper.toCoinEntityListFromAPI
import com.example.coingeckoapi.data.coinMapper.toCoinModelList
import com.example.coingeckoapi.data.remote.CoingeckoAPIService
import com.example.coingeckoapi.data.roomdb.CoinEntity
import com.example.coingeckoapi.data.roomdb.dao.CoinDao
import com.example.coingeckoapi.domain.model.CoinModel
import com.example.coingeckoapi.domain.helper.toUserFriendlyMessage
import com.example.coingeckoapi.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val dao: CoinDao,
    private val apiService: CoingeckoAPIService
) : CoinRepository {


    override fun getCoinList(ids: String?): Flow<List<CoinModel>> {
        return dao.getAllCoin().map { it.toCoinModelList() }
    }

    override suspend fun refreshCoins(): Result<Unit> {
        return try {
            val response = apiService.getCoinMarkets()
            dao.upsertCoin(response.toCoinEntityListFromAPI())
            Result.success(Unit)
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            val friendlyMessage = e.toUserFriendlyMessage()
            Result.failure(Exception(friendlyMessage))
        }
    }

    override fun getCoinsById(ids: Set<String>): Flow<List<CoinModel>> {
        return dao.getCoinsById(ids).map {
            it.toCoinModelList()
        }
    }
}