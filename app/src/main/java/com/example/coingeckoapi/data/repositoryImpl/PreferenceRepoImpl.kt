package com.example.coingeckoapi.data.repositoryImpl

import com.example.coingeckoapi.data.usrPreferences.UserPreferences
import com.example.coingeckoapi.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferenceRepoImpl @Inject constructor(
    private val userPreferences: UserPreferences
) : PreferenceRepository {
    override fun getUser(): Flow<Boolean> {
        return userPreferences.getUser
    }

    override suspend fun saveUser(isAvailable: Boolean) {
        userPreferences.saveUser(isAvailable)
    }

    override suspend fun addToWatchlist(id: String): Boolean {
        return userPreferences.addToWatchlist(id)
    }

    override fun getAllId(): Flow<Set<String>> {
         return userPreferences.getWatchlist
    }

    override suspend fun removeFromWatchList(id: String) {
        userPreferences.removeFromWatchlist(id)
    }

}