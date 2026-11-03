package com.example.coingeckoapi.domain.usacases.transactionUseCases

import com.example.coingeckoapi.domain.model.TransactionModel
import com.example.coingeckoapi.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetTransactionById @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(id: String): Flow<List<TransactionModel>> {
        return repository.getTransactionsById(id)
    }
}