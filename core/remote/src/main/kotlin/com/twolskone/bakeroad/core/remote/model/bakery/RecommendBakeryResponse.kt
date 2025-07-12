package com.twolskone.bakeroad.core.remote.model.bakery

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendBakeryResponse(
    @SerialName("bakery_id")
    val bakeryId: Int = -1,
    @SerialName("name")
    val name: String = "",
    @SerialName("avg_rating")
    val avgRating: Float = -1f,
    @SerialName("review_count")
    val reviewCount: Int = 0,
    @SerialName("is_opened")
    val isOpened: Boolean = false,
    @SerialName("img_url")
    val imgUrl: String = ""
)