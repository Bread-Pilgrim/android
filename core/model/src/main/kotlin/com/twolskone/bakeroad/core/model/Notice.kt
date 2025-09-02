package com.twolskone.bakeroad.core.model

/**
 * 공지사항
 * @param id        공지 ID
 * @param title     공지 제목
 * @param content   공지 내용
 */
data class Notice(
    val id: Int,
    val title: String,
    val content: String
)