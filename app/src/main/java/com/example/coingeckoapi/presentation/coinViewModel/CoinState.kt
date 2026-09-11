package com.example.coingeckoapi.presentation.coinViewModel

import com.example.coingeckoapi.domain.model.CoinModel
import com.example.coingeckoapi.domain.model.UserModel

data class CoinState(
    val isSuccess : Boolean = false,
    val isLoading : Boolean = false,
    val isError : String = "",
    val coinList : List<CoinModel> = emptyList()
)

data class CoinDetailState(
    val isSuccess : Boolean = false,
    val isLoading : Boolean = false,
    val isError : String = "",
    val coin : CoinModel? = null
)

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
}

data class UserState(
    val isSuccess : Boolean = false,
    val isLoading : Boolean = false,
    val isError : String = "",
    val userList : List<UserModel> = emptyList()
)

data class UserDetailState(
    val isLoading : Boolean = false,
    val user : UserModel? = null
)

