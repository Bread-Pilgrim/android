package com.twolskone.bakeroad.core.domain.repository

import com.twolskone.bakeroad.core.model.SelectedPreferenceOptions
import kotlinx.coroutines.flow.Flow

interface OnboardingRepository {
    suspend fun isOnboardingCompleted(): Boolean
    suspend fun setOnboardingCompleted(value: Boolean)
    fun postOnboarding(nickname: String, selectedPreferenceOptions: SelectedPreferenceOptions): Flow<String>
}