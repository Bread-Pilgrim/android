package com.twolskone.bakeroad.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface CacheRepository {
    suspend fun isOnboardingCompleted(): Boolean
    suspend fun setOnboardingCompleted(value: Boolean)
}