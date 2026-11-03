package com.example.coingeckoapi.data.coinMapper

import com.example.coingeckoapi.data.roomdb.entity.TransactionEntity
import com.example.coingeckoapi.domain.model.TransactionModel

fun TransactionModel.toEntity() : TransactionEntity {
    return TransactionEntity(
        id = id,
        coinId = coinId,
        coinHold = coinHold,
        name = name,
        symbol = symbol,
        quantity = quantity,
        transactionType = transactionType,
        buyPrice = buyPrice,
        sellPrice = sellPrice,
        total = total,
        timestamp = timestamp
    )
}
fun TransactionEntity.toDomain(): TransactionModel {
    return TransactionModel(
        id = id,
        coinId = coinId,
        coinHold = coinHold,
        name = name,
        symbol = symbol,
        quantity = quantity,
        transactionType = transactionType,
        buyPrice = buyPrice,
        sellPrice = sellPrice,
        total = total,
        timestamp = timestamp
    )
}
