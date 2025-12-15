package com.duocuc.serena.data.remote.user.Auth

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "auth_prefs"
)

class AuthDataStore(private val context: Context) {

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val USER_ID = longPreferencesKey("user_id")
    }

    // ---------------- TOKENS ----------------

    suspend fun saveTokens(access: String, refresh: String) {
        context.dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = access
            prefs[REFRESH_TOKEN] = refresh
        }
    }

    val accessToken: Flow<String?> =
        context.dataStore.data.map { it[ACCESS_TOKEN] }

    val refreshToken: Flow<String?> =
        context.dataStore.data.map { it[REFRESH_TOKEN] }

    // ---------------- USER ID ----------------

    suspend fun saveUserId(userId: Long?) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = userId as Long
        }
    }

    val userId: Flow<Long?> =
        context.dataStore.data.map { it[USER_ID] }

    // ---------------- CLEAR ----------------

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
