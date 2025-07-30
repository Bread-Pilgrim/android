package com.twolskone.bakeroad.core.remote.model.bakery

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BakeryLikeResponse(
    @SerialName("is_like")
    val isLike: Boolean,
    @SerialName("bakery_id")
    val bakeryId: Int
)