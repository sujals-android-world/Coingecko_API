package com.example.coingeckoapi.data.repositoryImpl

import com.example.coingeckoapi.data.coinMapper.toUserEntity
import com.example.coingeckoapi.data.coinMapper.toUserModel
import com.example.coingeckoapi.data.roomdb.dao.UserDao
import com.example.coingeckoapi.domain.model.UserModel
import com.example.coingeckoapi.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val dao : UserDao
) : UserRepository {


    override suspend fun createUser(user: UserModel): Result<Unit> {
        return try {
            dao.createUser(user.toUserEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(user: UserModel): Result<Unit> {
        return try {
            dao.updateUser(user.toUserEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteUser(user: UserModel) {
        dao.deleteUser(user.toUserEntity())
    }

    override  fun getUserDetail(): Flow<UserModel> {
        return dao.getUserDetail().map { it.toUserModel() }
    }
}