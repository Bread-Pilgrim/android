package com.twolskone.bakeroad.core.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Paging(
    @SerialName("cursor")
    val cursor: Cursor = Cursor()
) {

    @Serializable
    data class Cursor(
        @SerialName("before")
        val before: Int = 0,
        @SerialName("after")
        val after: Int = 0
    )
}