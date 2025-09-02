package com.twolskone.bakeroad.core.remote.model.notice

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoticeResponse(
    @SerialName("notice_id")
    val noticeId: Int = -1,
    @SerialName("notice_title")
    val noticeTitle: String = "",
    @SerialName("contents")
    val contents: List<String> = emptyList()
)