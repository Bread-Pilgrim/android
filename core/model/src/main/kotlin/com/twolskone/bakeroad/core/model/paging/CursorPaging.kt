package com.twolskone.bakeroad.core.model.paging

const val StartCursor = "0" // 시작 커서

/**
 * 커서 페이징
 * @property list           전체 데이터 목록
 * @property currentCursor  현재 커서
 * @property nextCursor     다음 커서
 */
data class CursorPaging<T>(
    val list: List<T>,
    val currentCursor: String,
    val nextCursor: String
)