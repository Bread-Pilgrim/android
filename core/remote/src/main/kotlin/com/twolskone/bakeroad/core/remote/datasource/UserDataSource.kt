package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.user.MyBakeryReviewsResponse
import com.twolskone.bakeroad.core.remote.model.user.UserOnboardingRequest
import com.twolskone.bakeroad.core.remote.model.user.UserPreferencesRequest
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun postOnboarding(request: UserOnboardingRequest): Flow<String>
    fun patchPreferences(request: UserPreferencesRequest): Flow<Unit>
    suspend fun getMyReviews(pageNo: Int, pageSize: Int): MyBakeryReviewsResponse
}