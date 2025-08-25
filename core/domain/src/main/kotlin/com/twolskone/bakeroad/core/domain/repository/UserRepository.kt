package com.twolskone.bakeroad.core.domain.repository

import com.twolskone.bakeroad.core.model.MyBakeryReview
import com.twolskone.bakeroad.core.model.PreferenceOptionIds
import com.twolskone.bakeroad.core.model.Profile
import com.twolskone.bakeroad.core.model.ReportDate
import com.twolskone.bakeroad.core.model.ReportDetail
import com.twolskone.bakeroad.core.model.paging.CursorPaging
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getOnboardingCompleted(): Boolean
    suspend fun setOnboardingCompleted(value: Boolean)
    fun postOnboarding(nickname: String, selectedPreferenceOptions: PreferenceOptionIds): Flow<Unit>
    fun patchPreferences(addPreferences: List<Int>, deletePreferences: List<Int>): Flow<Unit>
    fun getMyReviews(cursor: String): Flow<CursorPaging<MyBakeryReview>>
    fun getPreferences(): Flow<PreferenceOptionIds>
    fun getProfile(): Flow<Profile>
    fun getReportMonthlyList(cursor: String): Flow<CursorPaging<ReportDate>>
    fun getReportDetail(year: Int, month: Int): Flow<ReportDetail>
    fun enableBadge(badgeId: Int): Flow<Unit>
    fun disableBadge(badgeId: Int): Flow<Unit>
}