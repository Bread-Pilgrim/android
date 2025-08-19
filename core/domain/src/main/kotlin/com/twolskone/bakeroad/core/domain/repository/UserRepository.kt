package com.twolskone.bakeroad.core.domain.repository

import com.twolskone.bakeroad.core.model.MyBakeryReview
import com.twolskone.bakeroad.core.model.SelectedPreferenceOptions
import com.twolskone.bakeroad.core.model.paging.Paging
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getOnboardingCompleted(): Boolean
    suspend fun setOnboardingCompleted(value: Boolean)
    fun postOnboarding(nickname: String, selectedPreferenceOptions: SelectedPreferenceOptions): Flow<String>
    fun patchPreferences(addPreferences: List<Int>, deletePreferences: List<Int>): Flow<Unit>
    fun getMyReviews(page: Int): Flow<Paging<MyBakeryReview>>
}