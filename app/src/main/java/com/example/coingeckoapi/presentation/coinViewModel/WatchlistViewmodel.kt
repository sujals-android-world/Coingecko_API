package com.example.coingeckoapi.presentation.coinViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coingeckoapi.domain.model.CoinModel
import com.example.coingeckoapi.domain.repository.CoinRepository
import com.example.coingeckoapi.domain.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewmodel @Inject constructor (
    private val coinRepository: CoinRepository,
    private val repository: PreferenceRepository
)  : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val watchlistCoins: StateFlow<List<CoinModel>> =
        repository.getAllId()
            .flatMapLatest { ids -> coinRepository.getCoinsById(ids) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun removeFromWatchList(id : String) {
        viewModelScope.launch {
            repository.removeFromWatchList(id)
        }
    }
}