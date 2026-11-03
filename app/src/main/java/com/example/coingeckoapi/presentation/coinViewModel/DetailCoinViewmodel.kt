package com.example.coingeckoapi.presentation.coinViewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coingeckoapi.domain.usacases.allCoinUseCases.CoinUseCases
import com.example.coingeckoapi.domain.usacases.transactionUseCases.AllTransactionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailCoinViewmodel @Inject constructor(
    private val coinUseCases: CoinUseCases,
    private val transactionUseCases : AllTransactionUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    val coinDetailState : StateFlow<CoinDetailState> = savedStateHandle.get<String>("coinId")?.let { coinId ->
        coinUseCases.getCoin(ids = coinId)
            .map { list ->
                CoinDetailState(coin = list.firstOrNull(), isLoading = false)
            }
            .onStart {
                emit(CoinDetailState(isLoading = true))
            }
            .catch { e ->
                emit(CoinDetailState(isError = e.localizedMessage ?: "An unexpected error occurred", isLoading = false))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CoinDetailState(isLoading = true)
            )
    } ?: kotlinx.coroutines.flow.flowOf(CoinDetailState(isError = "Coin ID not found"))
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CoinDetailState(isError = "Coin ID not found")
        )

    val transactionState : StateFlow<TransactionByIDState>? = savedStateHandle.get<String>("coinId")?.let { coinId ->
        transactionUseCases.getTransactionById(coinId)
            .map { transaction ->
                TransactionByIDState(transaction = transaction, isLoading = false)
            }
            .onStart {
                TransactionByIDState(isLoading = true)
            }
            .catch { e ->
                TransactionByIDState(error = e.localizedMessage ?: "An unexpected error occurred", isLoading = false)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = TransactionByIDState(isLoading = true)
            )
    }
}


