package com.twolskone.bakeroad.core.data.repository

import com.twolskone.bakeroad.core.datastore.CacheDataSource
import com.twolskone.bakeroad.core.domain.repository.OnboardingRepository
import com.twolskone.bakeroad.core.model.SelectedPreferenceOptions
import com.twolskone.bakeroad.core.remote.datasource.UserDataSource
import com.twolskone.bakeroad.core.remote.model.user.UserOnboardingRequest
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

internal class OnboardingRepositoryImpl @Inject constructor(
    private val cacheDataSource: CacheDataSource,
    private val userDataSource: UserDataSource
) : OnboardingRepository {

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
}