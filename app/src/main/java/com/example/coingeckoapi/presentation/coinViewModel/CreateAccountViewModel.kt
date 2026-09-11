package com.example.coingeckoapi.presentation.coinViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coingeckoapi.domain.model.UserModel
import com.example.coingeckoapi.domain.usacases.allUserUseCases.AllUserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val useCases: AllUserUseCases
) : ViewModel() {

    private val _createAccountResult = MutableStateFlow<Result<Unit>?>(null)
    val createAccountResult = _createAccountResult.asStateFlow()

    fun createUser(userModel: UserModel) {
        viewModelScope.launch {
            val result = useCases.createUserUseCase(userModel)
            _createAccountResult.emit(result)
        }
    }
}

