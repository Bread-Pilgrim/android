package com.twolskone.bakeroad.core.data.repository

import com.twolskone.bakeroad.core.datastore.CacheDataSource
import com.twolskone.bakeroad.core.domain.repository.CacheRepository
import javax.inject.Inject

internal class CacheRepositoryImpl @Inject constructor(
    private val cacheDataSource: CacheDataSource
) : CacheRepository {

    override suspend fun isOnboardingCompleted(): Boolean =
        cacheDataSource.isOnboardingCompleted()

    override suspend fun setOnboardingCompleted(value: Boolean) {
        cacheDataSource.setOnboardingCompleted(value)
    }
}