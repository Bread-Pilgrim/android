package com.twolskone.bakeroad.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.twolskone.bakeroad.core.common.kotlin.extension.orFalse
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber

class CacheDataSource @Inject constructor(@CacheDataStore private val dataStore: DataStore<Preferences>) {

    private companion object {
        private val KEY_IS_ONBOARDING_COMPLETED = booleanPreferencesKey("is_onboarding_completed")
    }

    suspend fun isOnboardingCompleted(): Boolean =
        dataStore.data.firstOrNull()?.get(KEY_IS_ONBOARDING_COMPLETED).orFalse()

    suspend fun setOnboardingCompleted(value: Boolean) {
        runCatching {
            Timber.i("setOnboardingCompleted >> $value")
            dataStore.edit { preferences -> preferences[KEY_IS_ONBOARDING_COMPLETED] = value }
        }.getOrElse { cause ->
            Timber.e(cause)
        }
    }
}