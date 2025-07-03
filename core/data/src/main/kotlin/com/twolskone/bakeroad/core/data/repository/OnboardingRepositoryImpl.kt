package com.twolskone.bakeroad.core.data.repository

import com.twolskone.bakeroad.core.datastore.CacheDataSource
import com.twolskone.bakeroad.core.domain.repository.OnboardingRepository
import javax.inject.Inject

internal class OnboardingRepositoryImpl @Inject constructor(
    private val cacheDataSource: CacheDataSource
) : OnboardingRepository {

    override suspend fun isOnboardingCompleted(): Boolean {
        return cacheDataSource.isOnboardingCompleted()
    }

    override suspend fun setOnboardingCompleted(value: Boolean) {
        cacheDataSource.setOnboardingCompleted(value)
    }
}