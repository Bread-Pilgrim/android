package com.twolskone.bakeroad.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.twolskone.bakeroad.core.common.kotlin.extension.orFalse
import com.twolskone.bakeroad.core.datastore.di.CacheDataStore
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber

class CacheDataSource @Inject constructor(@CacheDataStore private val dataStore: DataStore<Preferences>) {

    private val gson = Gson()

    private companion object {
        private val KEY_IS_ONBOARDING_COMPLETED = booleanPreferencesKey("is_onboarding_completed")
        private val KEY_RECENT_SEARCH_QUERY = stringPreferencesKey("recent_search_query")

        private const val MAX_COUNT_RECENT_SEARCH_QUERY = 20
    }

    suspend fun getOnboardingCompleted(): Boolean =
        dataStore.data.firstOrNull()?.get(KEY_IS_ONBOARDING_COMPLETED).orFalse()

    suspend fun setOnboardingCompleted(value: Boolean) {
        runCatching {
            dataStore.edit { preferences -> preferences[KEY_IS_ONBOARDING_COMPLETED] = value }
        }.getOrElse { cause ->
            Timber.e(cause)
        }
    }

    suspend fun getRecentSearchQueries(): List<Pair<String, String>> {
        val string = dataStore.data.firstOrNull()?.get(KEY_RECENT_SEARCH_QUERY).orEmpty()
        return runCatching {
            val queries = if (string.isNotEmpty()) {
                gson.fromJson<List<Pair<String, String>>>(string, object : TypeToken<List<Pair<String, String>>>() {}.type)
            } else {
                emptyList()
            }
            queries.sortedByDescending { it.second }
        }.getOrElse { cause ->
            Timber.e(cause)
            emptyList()
        }
    }

    suspend fun putRecentSearchQuery(query: String) {
        dataStore.edit { preferences ->
            val mutableSearchQueries = getRecentSearchQueries().toMutableList()
            val index = mutableSearchQueries.indexOfFirst { it.first == query }
            if (index >= 0) {
                mutableSearchQueries[index] = (query to System.currentTimeMillis().toString())
            } else {
                mutableSearchQueries.add(0, query to System.currentTimeMillis().toString())
            }
            if (mutableSearchQueries.size > MAX_COUNT_RECENT_SEARCH_QUERY) {
                mutableSearchQueries.removeLastOrNull()
            }
            preferences[KEY_RECENT_SEARCH_QUERY] = gson.toJson(mutableSearchQueries)
        }
    }

    suspend fun deleteRecentSearchQuery(query: String) {
        dataStore.edit { preferences ->
            val mutableSearchQueries = getRecentSearchQueries().toMutableList()
            preferences[KEY_RECENT_SEARCH_QUERY] = gson.toJson(mutableSearchQueries.filter { it.first != query })
        }
    }

    suspend fun clearRecentSearchQueries() {
        dataStore.edit { preferences -> preferences.remove(KEY_RECENT_SEARCH_QUERY) }
    }

    suspend fun clearAll() {
        dataStore.edit { preferences -> preferences.clear() }
    }
}