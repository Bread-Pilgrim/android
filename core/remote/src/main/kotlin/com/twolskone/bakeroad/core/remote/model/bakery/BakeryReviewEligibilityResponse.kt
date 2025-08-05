package com.twolskone.bakeroad.core.remote.model.bakery

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BakeryReviewEligibilityResponse(
    @SerialName("is_eligible")
    val isEligible: Boolean = false
)