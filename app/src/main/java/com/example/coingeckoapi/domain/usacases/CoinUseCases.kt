package com.example.coingeckoapi.domain.usacases

data class CoinUseCases(
    val getCoin: GetAllCoin,
    val refreshCoins : RefreshCoinUseCase,
    val getCoinById : GetCoinByIdUseCase
)