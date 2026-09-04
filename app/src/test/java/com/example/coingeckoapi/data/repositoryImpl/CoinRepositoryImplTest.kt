package com.example.coingeckoapi.data.repositoryImpl

import androidx.room.Dao
import com.example.coingeckoapi.data.remote.CoingeckoAPIService
import com.example.coingeckoapi.data.roomdb.dao.CoinDao
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class CoinRepositoryImplTest {

    private val provideCoingeckoAPIService : CoingeckoAPIService = mock()
    private val coinDao : CoinDao = mock()

//    @Test
//    fun test_success() = runTest {
//
//        `when`()
//
//        val repo = CoinRepositoryImpl(coinDao,provideCoingeckoAPIService)
//
//        var result = repo.refreshCoins()
//
//
//    }

}