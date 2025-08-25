package com.twolskone.bakeroad.core.remote.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportResponse(
    @SerialName("year")
    val year: Int = 0,
    @SerialName("month")
    val month: Int = 0,
    @SerialName("visited_areas")
    val visitedAreas: Map<String, Int> = emptyMap(),
    @SerialName("bread_types")
    val breadTypes: Map<String, Int> = emptyMap(),
    @SerialName("daily_avg_quantity")
    val dailyAvgQuantity: Float = 0f,
    @SerialName("monthly_consumption_gap")
    val monthlyConsumptionGap: Float = 0f,
    @SerialName("total_quantity")
    val totalQuantity: Int = 0,
    @SerialName("total_visit_count")
    val totalVisitCount: Int = 0,
    @SerialName("total_prices")
    val totalPrices: List<Int> = emptyList(),
    @SerialName("weekly_distribution")
    val weeklyDistribution: Map<String, Int> = emptyMap(),
    @SerialName("review_count")
    val reviewCount: Int = 0,
    @SerialName("liked_count")
    val likedCount: Int = 0,
    @SerialName("received_likes_count")
    val receivedLikesCount: Int = 0
)
