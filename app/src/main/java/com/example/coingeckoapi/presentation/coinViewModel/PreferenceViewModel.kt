package com.example.coingeckoapi.presentation.coinViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coingeckoapi.domain.repository.PreferenceRepository
import com.example.coingeckoapi.domain.usacases.allUserUseCases.AllUserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferenceViewModel @Inject constructor(
    private val useCases: AllUserUseCases
) : ViewModel() {

    val isUserAvailable: StateFlow<Boolean> = useCases.getUserUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun saveUser(user : Boolean) {
        viewModelScope.launch {
            useCases.saveUserUseCase(user)
        }
    }

}