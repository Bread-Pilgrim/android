package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.extra.BadgeExtraResponse
import com.twolskone.bakeroad.core.remote.model.user.MyBakeryReviewsResponse
import com.twolskone.bakeroad.core.remote.model.user.OnboardingRequest
import com.twolskone.bakeroad.core.remote.model.user.PreferencesGetResponse
import com.twolskone.bakeroad.core.remote.model.user.PreferencesPatchRequest
import com.twolskone.bakeroad.core.remote.model.user.ProfileResponse
import com.twolskone.bakeroad.core.remote.model.user.ReportMonthlyListResponse
import com.twolskone.bakeroad.core.remote.model.user.ReportResponse
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun postOnboarding(request: OnboardingRequest): Flow<List<BadgeExtraResponse>>
    fun patchPreferences(request: PreferencesPatchRequest): Flow<Unit>
    suspend fun getMyReviews(cursor: String, pageSize: Int): MyBakeryReviewsResponse
    fun getPreferences(): Flow<PreferencesGetResponse>
    fun getProfile(): Flow<ProfileResponse>
    suspend fun getReportMonthlyList(cursor: String, pageSize: Int): ReportMonthlyListResponse
    fun getReport(year: Int, month: Int): Flow<ReportResponse>
    fun enableBadge(badgeId: Int): Flow<Unit>
    fun disableBadge(badgeId: Int): Flow<Unit>
    fun deleteAccount(): Flow<Unit>
}