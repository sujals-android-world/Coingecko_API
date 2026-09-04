package com.example.coingeckoapi.presentation.coinViewModel

import com.example.coingeckoapi.domain.dto.CoinModel

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