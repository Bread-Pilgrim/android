package com.twolskone.bakeroad.core.remote.model.tour

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TourAreaEventResponse(
    @SerialName("title")
    val title: String = "",
    @SerialName("address")
    val address: String = "",
    @SerialName("start_date")
    val startDate: String = "",     // yyyy-MM-dd
    @SerialName("end_date")         // yyyy-MM-dd
    val endDate: String = "",
    @SerialName("event_img")
    val eventImg: String = "",
    @SerialName("mapx")
    val mapX: Int = 0,
    @SerialName("mapy")
    val mapY: Int = 0,
    @SerialName("tel")
    val tel: String = ""
)