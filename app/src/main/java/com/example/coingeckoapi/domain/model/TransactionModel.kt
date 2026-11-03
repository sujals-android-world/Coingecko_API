package com.example.coingeckoapi.domain.model

data class TransactionModel(
    val id: Long = 0,
    val coinId: String,
    val coinHold : Double?,
    val name: String,
    val symbol: String,
    val quantity: Double,
    val transactionType: String,
    val buyPrice : Double?,
    val sellPrice : Double?,
    val total : Double?,
    val timestamp: Long = System.currentTimeMillis()
)