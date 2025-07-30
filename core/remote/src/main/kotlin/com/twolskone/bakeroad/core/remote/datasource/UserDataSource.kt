package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.user.UserOnboardingRequest
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun postOnboarding(request: UserOnboardingRequest): Flow<String>
}