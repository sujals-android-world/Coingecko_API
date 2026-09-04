package com.example.coingeckoapi.presentation.navigation

object Routes {
    const val COIN_LIST = "coinList"
    const val COIN_DETAIL = "coinDetail/{coinId}"

    fun passCoinId(coinId: String) = "coinDetail/$coinId"
}