package com.example.coingeckoapi.data.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
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
