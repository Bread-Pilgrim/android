package com.twolskone.bakeroad.core.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Paging(
    @SerialName("next_cursor")
    val nextCursor: String = "",
    @SerialName("has_next")
    val hasNext: Boolean = false
)