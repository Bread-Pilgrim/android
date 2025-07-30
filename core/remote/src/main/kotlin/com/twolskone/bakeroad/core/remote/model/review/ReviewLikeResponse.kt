package com.twolskone.bakeroad.core.remote.model.review

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewLikeResponse(
    @SerialName("is_like")
    val isLike: Boolean,
    @SerialName("review_id")
    val reviewId: Int
)