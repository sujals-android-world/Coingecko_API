package com.example.coingeckoapi.data.repositoryImpl

import com.example.coingeckoapi.data.coinMapper.toDomain
import com.example.coingeckoapi.data.coinMapper.toEntity
import com.example.coingeckoapi.data.roomdb.dao.TransactionDao
import com.example.coingeckoapi.domain.model.TransactionModel
import com.example.coingeckoapi.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepoImpl @Inject constructor(
    private val dao : TransactionDao
) : TransactionRepository {
    override suspend fun insertTransaction(transactionModel: TransactionModel): Result<Unit> {
        try {
            dao.insertTransaction(transactionModel.toEntity())
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override fun getAllTransaction(): Flow<List<TransactionModel>> {
        return dao.getAllTransactions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTransactionsById(coinID: String): Flow<List<TransactionModel>> {
        return dao.getTransactionsById(coinID).map { transactionEntities -> transactionEntities.map { it.toDomain() } }
    }
}