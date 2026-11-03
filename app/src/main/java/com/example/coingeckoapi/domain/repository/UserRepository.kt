package com.example.coingeckoapi.domain.repository

import com.example.coingeckoapi.domain.model.UserModel
import kotlinx.coroutines.flow.Flow


interface UserRepository {


    suspend fun createUser(user : UserModel) : Result<Unit>

    suspend fun updateUser(user: UserModel): Result<Unit>

    suspend fun deleteUser(user : UserModel)

    fun getUserDetail()  : Flow<UserModel?>


}