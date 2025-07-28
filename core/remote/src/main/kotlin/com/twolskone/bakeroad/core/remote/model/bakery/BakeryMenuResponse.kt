package com.twolskone.bakeroad.core.remote.model.bakery

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BakeryMenuResponse(
    @SerialName("menu_id")
    val menuId: Int = -1,
    @SerialName("menu_name")
    val menuName: String = "",
    @SerialName("is_signature")
    val isSignature: Boolean = false
)