package com.twolskone.bakeroad.core.model.paging

// TODO. cursor paging 적용 후 삭제
const val startPage = 1 // 시작 페이지

/**
 * TODO. cursor paging 적용 후 삭제
 * 페이징
 * @property list       전체 데이터 목록
 * @property page       현재 페이지
 * @property hasNext    다음 페이지 여부
 */
data class Paging<T>(
    val list: List<T>,
    val page: Int,
    val hasNext: Boolean
)