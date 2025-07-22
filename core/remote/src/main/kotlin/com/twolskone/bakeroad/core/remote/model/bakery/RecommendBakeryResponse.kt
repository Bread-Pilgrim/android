package com.twolskone.bakeroad.core.remote.model.bakery

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendBakeryResponse(
    @SerialName("bakery_id")
    val bakeryId: Int = -1,
    @SerialName("bakery_name")
    val bakeryName: String = "",
    @SerialName("commercial_area_id")
    val commercialAreaId: Int,
    @SerialName("avg_rating")
    val avgRating: Float = -1f,
    @SerialName("review_count")
    val reviewCount: Double = 0.0,
    @SerialName("open_status")
    val openStatus: String = "O",  // O : 영업중, C : 영업종료, D : 휴무일
    @SerialName("img_url")
    val imgUrl: String = ""
)