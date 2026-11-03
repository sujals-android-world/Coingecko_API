package com.example.coingeckoapi.domain.repository

import com.example.coingeckoapi.domain.model.TransactionModel
import kotlinx.coroutines.flow.Flow


interface TransactionRepository {


    suspend fun insertTransaction(transactionModel: TransactionModel) : Result<Unit>

    fun getAllTransaction() : Flow<List<TransactionModel>>

    fun getTransactionsById(coinID : String) : Flow<List<TransactionModel>>
}