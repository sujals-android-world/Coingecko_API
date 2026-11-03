package com.example.coingeckoapi.presentation.coinViewModel

import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coingeckoapi.domain.usacases.allUserUseCases.AllUserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val  useCases: AllUserUseCases
) : ViewModel() {

    val userDetail: StateFlow<UserDetailState> = useCases.getUserDetailUseCase()
        .map { data ->
            UserDetailState(user = data)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserDetailState(isLoading = true)
        )
}
