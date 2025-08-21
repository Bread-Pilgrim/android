package com.twolskone.bakeroad.core.remote.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportMonthlyListResponse(
    @SerialName("next_cursor")
    val nextCursor: String = "",
    @SerialName("items")
    val items: List<ReportMonthlyResponse> = emptyList()
)

@Serializable
data class ReportMonthlyResponse(
    @SerialName("year")
    val year: Int = 0,
    @SerialName("month")
    val month: Int = 0
)