package com.example.coingeckoapi.data.usrPreferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
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
    }



    suspend fun saveUser(isAvailable : Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_USER_AVAILABLE] = isAvailable
        }
    }


    val getUser: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_USER_AVAILABLE] ?: false
    }

}