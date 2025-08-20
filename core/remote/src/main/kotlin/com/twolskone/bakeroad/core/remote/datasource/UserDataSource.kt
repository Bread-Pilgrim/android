package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.user.MyBakeryReviewsResponse
import com.twolskone.bakeroad.core.remote.model.user.OnboardingRequest
import com.twolskone.bakeroad.core.remote.model.user.PreferencesGetResponse
import com.twolskone.bakeroad.core.remote.model.user.PreferencesPatchRequest
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun postOnboarding(request: OnboardingRequest): Flow<String>
    fun patchPreferences(request: PreferencesPatchRequest): Flow<Unit>
    suspend fun getMyReviews(pageNo: Int, pageSize: Int): MyBakeryReviewsResponse
    fun getPreferences(): Flow<PreferencesGetResponse>
}