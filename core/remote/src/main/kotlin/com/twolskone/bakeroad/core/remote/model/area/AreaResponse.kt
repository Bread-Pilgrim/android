package com.twolskone.bakeroad.core.remote.model.area

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AreaResponse(
    @SerialName("area_code")
    val areaCode: Int = -1,
    @SerialName("area_name")
    val areaName: String = ""
)