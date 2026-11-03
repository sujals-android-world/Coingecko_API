package com.example.coingeckoapi.data.usrPreferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val IS_USER_AVAILABLE = booleanPreferencesKey("is_user_available")
        private val WATCHLIST_IDS = stringSetPreferencesKey("watchlist_ids")
    }


    suspend fun saveUser(isAvailable : Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_USER_AVAILABLE] = isAvailable
        }
    }

    val getUser: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_USER_AVAILABLE] ?: false
    }

    val getWatchlist : Flow<Set<String>> = context.dataStore.data.map { preferences ->
        preferences[WATCHLIST_IDS] ?: emptySet()
    }

    suspend fun addToWatchlist(id : String) : Boolean {
        var added = false
        context.dataStore.edit { preferences ->
            val current = preferences[WATCHLIST_IDS] ?: emptySet()
            if(id !in current) {
                preferences[WATCHLIST_IDS] = current + id
                added = true
            }
        }
        return added
    }

    suspend fun removeFromWatchlist(id: String) {
        context.dataStore.edit { preferences ->
            val current = preferences[WATCHLIST_IDS] ?: emptySet()
            if (id in current) {
                preferences[WATCHLIST_IDS] = current - id
            }
        }
    }
}