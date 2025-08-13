package com.twolskone.bakeroad.core.data.repository

import com.twolskone.bakeroad.core.datastore.CacheDataSource
import com.twolskone.bakeroad.core.domain.repository.UserRepository
import com.twolskone.bakeroad.core.model.SelectedPreferenceOptions
import com.twolskone.bakeroad.core.remote.datasource.UserDataSource
import com.twolskone.bakeroad.core.remote.model.user.UserOnboardingRequest
import com.twolskone.bakeroad.core.remote.model.user.UserPreferencesRequest
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

internal class UserRepositoryImpl @Inject constructor(
    private val cacheDataSource: CacheDataSource,
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun getOnboardingCompleted(): Boolean {
        return cacheDataSource.getOnboardingCompleted()
    }

    override suspend fun setOnboardingCompleted(value: Boolean) {
        cacheDataSource.setOnboardingCompleted(value)
    }

    override fun postOnboarding(nickname: String, selectedPreferenceOptions: SelectedPreferenceOptions): Flow<String> {
        val request = UserOnboardingRequest(
            nickname = nickname,
            breadTypes = selectedPreferenceOptions.breadTypes,
            flavors = selectedPreferenceOptions.flavors,
            atmospheres = selectedPreferenceOptions.atmospheres,
            commercialAreas = selectedPreferenceOptions.commercialAreas
        )
        return userDataSource.postOnboarding(request = request)
    }

    override fun patchPreferences(addPreferences: List<Int>, deletePreferences: List<Int>): Flow<Unit> {
        val request = UserPreferencesRequest(addPreferences = addPreferences, deletePreferences = deletePreferences)
        return userDataSource.patchPreferences(request)
    }
}