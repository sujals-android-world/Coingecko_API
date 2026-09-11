package com.example.coingeckoapi.presentation.coinViewModel

import com.example.coingeckoapi.domain.usacases.allCoinUseCases.CoinUseCases
import com.example.coingeckoapi.domain.usacases.allCoinUseCases.GetAllCoin
import com.example.coingeckoapi.domain.usacases.allCoinUseCases.RefreshCoinUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext

@ExtendWith(MainDispatcherExtension::class)
class MarketViewModelTest {

    private lateinit var coinUseCases: CoinUseCases
    private lateinit var viewModel: MarketViewModel

    private lateinit var getAllCoin: GetAllCoin
    private lateinit var refreshCoinUseCase: RefreshCoinUseCase

    @BeforeEach
    fun setup() {
        getAllCoin = mockk()
        refreshCoinUseCase = mockk()
        coinUseCases = CoinUseCases(
            getCoin = getAllCoin,
            refreshCoins = refreshCoinUseCase,
            getCoinById = mockk()  // use nahi kar rahe, bas relaxed rakhte hai
        )

        // default behavior taaki init block crash na kare
        coEvery { refreshCoinUseCase.invoke() } returns Result.success(Unit)
        every { getAllCoin.invoke(any()) } returns flowOf(emptyList())

        viewModel = MarketViewModel(coinUseCases)
    }

    @Test
    fun `setup runs without crashing`() {
        // khaali test, bas confirm karna hai setup crash toh nahi hua
    }

}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherExtension(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : BeforeEachCallback, AfterEachCallback {
    override fun beforeEach(context: ExtensionContext) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun afterEach(context: ExtensionContext) {
        Dispatchers.resetMain()
    }
}