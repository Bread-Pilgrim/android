package com.twolskone.bakeroad.core.remote.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserOnboardingRequest(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("bread_types")
    val breadTypes: List<Int>,
    @SerialName("flavors")
    val flavors: List<Int>,
    @SerialName("atmospheres")
    val atmospheres: List<Int>,
    @SerialName("commercial_areas")
    val commercialAreas: List<Int>
)