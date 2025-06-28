package com.twolskone.bakeroad.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber

class TokenDataSource @Inject constructor(@TokenDataStore private val dataStore: DataStore<Preferences>) {

    private companion object {
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    suspend fun getAccessToken(): String = dataStore.data.firstOrNull()?.get(KEY_ACCESS_TOKEN).orEmpty()

    suspend fun getTokens(): Pair<String, String> {
        val accessToken = getAccessToken()
        val refreshToken = dataStore.data.firstOrNull()?.get(KEY_REFRESH_TOKEN).orEmpty()

        return accessToken to refreshToken
    }

    suspend fun setTokens(accessToken: String, refreshToken: String) {
        runCatching {
            Timber.i("setTokens >> accessToken: $accessToken, refreshToken: $refreshToken")
            dataStore.edit { preferences ->
                preferences[KEY_ACCESS_TOKEN] = accessToken
                preferences[KEY_REFRESH_TOKEN] = refreshToken
            }
        }.getOrElse { cause ->
            Timber.e(cause)
        }
    }
}