package com.example.coingeckoapi.data.coinMapper

import com.example.coingeckoapi.data.roomdb.entity.UserEntity
import com.example.coingeckoapi.domain.model.UserModel

fun UserModel.toUserEntity() : UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        password = password,
        amount = amount
    )
}
fun UserEntity.toUserModel(): UserModel {
    return UserModel(
        id = id,
        name = name,
        email = email,
        password = password,
        amount = amount
    )
}
