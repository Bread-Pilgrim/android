package com.twolskone.bakeroad.core.remote.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("onboarding_completed")
    val isOnboardingCompleted: Boolean
)