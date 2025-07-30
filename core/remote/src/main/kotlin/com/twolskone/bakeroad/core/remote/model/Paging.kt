package com.twolskone.bakeroad.core.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

const val initialCursor = "0"
const val initialSortCursor = "0||0"

@Serializable
data class Paging(
    @SerialName("prev_cursor")
    val prevCursor: String = "",
    @SerialName("next_cursor")
    val nextCursor: String = "",
    @SerialName("has_next")
    val hasNext: Boolean = false
)