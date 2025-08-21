package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.user.MyBakeryReviewsResponse
import com.twolskone.bakeroad.core.remote.model.user.OnboardingRequest
import com.twolskone.bakeroad.core.remote.model.user.PreferencesGetResponse
import com.twolskone.bakeroad.core.remote.model.user.PreferencesPatchRequest
import com.twolskone.bakeroad.core.remote.model.user.ProfileResponse
import com.twolskone.bakeroad.core.remote.model.user.ReportMonthlyListResponse
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun postOnboarding(request: OnboardingRequest): Flow<Unit>
    fun patchPreferences(request: PreferencesPatchRequest): Flow<Unit>
    suspend fun getMyReviews(cursor: String, pageSize: Int): MyBakeryReviewsResponse
    fun getPreferences(): Flow<PreferencesGetResponse>
    fun getProfile(): Flow<ProfileResponse>
    suspend fun getReportMonthlyList(cursor: String, pageSize: Int): ReportMonthlyListResponse
}