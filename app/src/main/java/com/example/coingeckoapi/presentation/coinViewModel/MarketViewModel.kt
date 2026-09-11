package com.example.coingeckoapi.presentation.coinViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coingeckoapi.domain.usacases.allCoinUseCases.CoinUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val coinUseCases: CoinUseCases,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        startPeriodicCoinRefresh()
    }

    private fun startPeriodicCoinRefresh() {
        viewModelScope.launch {
            try {
                while (true) {
                    val result = coinUseCases.refreshCoins()

                    result.onFailure { exception ->
                        _eventFlow.emit(
                            UiEvent.ShowToast(
                                exception.localizedMessage ?: "Failed to refresh coins"
                            )
                        )
                    }
                    delay(10_000)
                }
            } catch (e: kotlinx.coroutines.CancellationException) {
                throw e
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val getAllCoin: StateFlow<CoinState> = _searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            coinUseCases.getCoin(ids = query.ifEmpty { null })
                .map { list ->
                    val filteredList = if (query.isEmpty()) {
                        list
                    } else {
                        list.filter { coin ->
                            coin.id.contains(query, ignoreCase = true) ||
                                    coin.symbol.contains(query, ignoreCase = true)
                        }
                    }
                    CoinState(coinList = filteredList, isLoading = false)
                }
                .onStart {
                    emit(CoinState(isLoading = true))
                }
                .catch { e ->
                    emit(CoinState(isLoading = false, isError = e.localizedMessage ?: "An unexpected error occurred"))
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CoinState(isLoading = true)
        )
}


