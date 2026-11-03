package com.example.coingeckoapi.presentation.coinViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coingeckoapi.domain.repository.PreferenceRepository
import com.example.coingeckoapi.domain.usacases.allUserUseCases.AllUserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class PreferenceViewModel @Inject constructor(
    private val useCases: AllUserUseCases,
    private val repository: PreferenceRepository
) : ViewModel() {



    val isUserAvailable: StateFlow<Boolean> = useCases.getUserUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    val watchlistIds: StateFlow<Set<String>> = repository.getAllId()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )



    private val _events = Channel<UiEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()


    fun saveUser(user : Boolean) {
        viewModelScope.launch {
            useCases.saveUserUseCase(user)
        }
    }
    fun addToWatchlist(id : String) {
        viewModelScope.launch {
            try {
                val added = repository.addToWatchlist(id)
                val message =
                    if (added) "SuccessFully Added To Watchlist" else "Already in Watchlist"
                _events.send(UiEvent.ShowToast(message))
            } catch (e: IOException) {
                _events.send(UiEvent.ShowToast("Could not save, try again"))
            }
        }
    }
}