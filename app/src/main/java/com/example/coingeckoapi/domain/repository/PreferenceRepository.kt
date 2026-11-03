package com.example.coingeckoapi.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository  {
    fun getUser() : Flow<Boolean>
    suspend fun saveUser(isAvailable: Boolean)

    suspend fun addToWatchlist(id : String) : Boolean

    fun getAllId() : Flow<Set<String>>

    suspend fun removeFromWatchList(id : String)
}