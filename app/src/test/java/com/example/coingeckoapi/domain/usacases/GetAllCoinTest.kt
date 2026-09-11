package com.example.coingeckoapi.domain.usacases

import com.example.coingeckoapi.data.remote.CoingeckoAPIService
import com.example.coingeckoapi.data.remote.response.CoinGeckoResponse
import com.example.coingeckoapi.data.remote.response.CoinGeckoResponseItem
import com.example.coingeckoapi.data.repositoryImpl.CoinFakeRepository
import com.example.coingeckoapi.data.repositoryImpl.CoinRepositoryImpl
import com.example.coingeckoapi.data.roomdb.dao.CoinDao
import com.example.coingeckoapi.domain.model.CoinModel
import com.example.coingeckoapi.domain.repository.CoinRepository
import com.example.coingeckoapi.domain.usacases.allCoinUseCases.GetAllCoin
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify


class GetAllCoinTest {

    @Test
    fun `getCoinList returns list of coins from repository`() {
        // Arrange — sab kuch taiyar karna
        val fakeRepository = CoinFakeRepository()
        fakeRepository.listCoinModel = listOf(createDummyCoin())
        val useCase = GetAllCoin(fakeRepository)

        // Act — jis function ko test karna hai use call karna
        val resultFlow = useCase.invoke()

        // Assert — check karna result sahi hai ya nahi
        runTest {
            val result = resultFlow.first()   // Flow se pehli value nikalna
            assertEquals(fakeRepository.listCoinModel, result)
        }
    }

    private val apiService = mockk<CoingeckoAPIService>()
    private val dao = mockk<CoinDao>()
    private lateinit var repository: CoinRepository


    @BeforeEach
    fun setup() {
        repository = CoinRepositoryImpl(dao,apiService)
    }

    @Test
    fun `refreshCoins returns success when API and DB both work`() = runTest {
        // Arrange
        val fakeApiResponse = listOf(createDummyCoin().toCoinApiResponse()).toCoinGeckoResponse()
        coEvery { apiService.getCoinMarkets() } returns fakeApiResponse
        coEvery { dao.upsertCoin(any()) } just Runs

        // Act
        val result = repository.refreshCoins()

        // Assert
        assertTrue(result.isSuccess)
    }

    @Test
    fun `refreshCoins returns failure when API throws exception`() = runTest {
        // Arrange
        coEvery { apiService.getCoinMarkets() } throws Exception()

        // Act
        val result = repository.refreshCoins()

        // Assert
        assertTrue(result.isFailure)
    }

    @Test
    fun `refreshCoins returns failure with correct message when API throws exception`() = runTest {
        // Arrange
        coEvery { apiService.getCoinMarkets() } throws Exception("Network error")

        // Act
        val result = repository.refreshCoins()

        val exception = result.exceptionOrNull()

        assertEquals("Network error", exception?.message)
    }

    @Test
    fun `refreshCoins handles empty API response correctly`() = runTest {
        // Arrange
        val emptyResponse = emptyList<CoinGeckoResponseItem>().toCoinGeckoResponse()
        coEvery { apiService.getCoinMarkets() } returns emptyResponse
        coEvery { dao.upsertCoin(any()) } just Runs

        // Act
        val result = repository.refreshCoins()

        // Assert
        assertTrue(result.isSuccess)
    }

    @Test
    fun `invoke passes correct ids to repository`() {
        // Arrange
        val fakeRepository = mockk<CoinRepository>()
        every { fakeRepository.getCoinList(any()) } returns flowOf(emptyList())
        val useCase = GetAllCoin(fakeRepository)

        // Act
        useCase.invoke(ids = "bitcoin")

        // Assert
        verify { fakeRepository.getCoinList(ids = "bitcoin") }
    }

    private fun every(function: Any) {}
}

fun List<CoinGeckoResponseItem>.toCoinGeckoResponse(): CoinGeckoResponse {
    val response = CoinGeckoResponse()
    response.addAll(this)
    return response
}

fun createDummyCoin(
    id: String = "bitcoin",
    name: String = "Bitcoin",
    currentPrice: Double = 50000.0
): CoinModel {
    return CoinModel(
        id = id,
        symbol = "btc",
        name = name,
        image = "https://example.com/bitcoin.png",
        current_price = currentPrice,
        market_cap = 900000000000.0.toLong(),
        market_cap_rank = 1,
        fully_diluted_valuation = 1000000000000.0.toLong(),
        total_volume = 25000000000.0.toLong(),
        high_24h = 51000.0,
        low_24h = 49000.0,
        price_change_24h = 500.0,
        price_change_percentage_24h = 1.0,
        market_cap_change_24h = 10000000000.0,
        market_cap_change_percentage_24h = 1.1,
        circulating_supply = 19000000.0,
        total_supply = 21000000.0,
        max_supply = 21000000.0,
        ath = 69000.0,
        ath_change_percentage = -27.5,
        ath_date = "2021-11-10T14:24:11.849Z",
        atl = 67.81,
        atl_change_percentage = 73000.0,
        atl_date = "2013-07-06T00:00:00.000Z",
        roi = null,
        last_updated = "2026-09-04T00:00:00.000Z"
    )
}

fun CoinModel.toCoinApiResponse() : CoinGeckoResponseItem {
    return CoinGeckoResponseItem (
        id = id,
        symbol = "btc",
        name = name,
        image = "https://example.com/bitcoin.png",
        current_price = current_price,
        market_cap = 900000000000.0.toLong(),
        market_cap_rank = 1,
        fully_diluted_valuation = 1000000000000.0.toLong(),
        total_volume = 25000000000.0.toLong(),
        high_24h = 51000.0,
        low_24h = 49000.0,
        price_change_24h = 500.0,
        price_change_percentage_24h = 1.0,
        market_cap_change_24h = 10000000000.0,
        market_cap_change_percentage_24h = 1.1,
        circulating_supply = 19000000.0,
        total_supply = 21000000.0,
        max_supply = 21000000.0,
        ath = 69000.0,
        ath_change_percentage = -27.5,
        ath_date = "2021-11-10T14:24:11.849Z",
        atl = 67.81,
        atl_change_percentage = 73000.0,
        atl_date = "2013-07-06T00:00:00.000Z",
        roi = null,
        last_updated = "2026-09-04T00:00:00.000Z"
    )
}