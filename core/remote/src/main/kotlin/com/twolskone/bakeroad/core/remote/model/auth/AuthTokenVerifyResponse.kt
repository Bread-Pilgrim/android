package com.twolskone.bakeroad.core.remote.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AuthTokenVerifyResponse(
    @SerialName("onboarding_completed")
    val isOnboardingCompleted: Boolean
)