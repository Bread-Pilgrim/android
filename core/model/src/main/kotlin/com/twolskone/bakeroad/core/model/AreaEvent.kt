package com.twolskone.bakeroad.core.model

/**
 * 지역 행사
 * @param title     행사 제목
 * @param address   행사 주소
 * @param startDate 행사 사작일 (yyyy.MM.dd)
 * @param endDate   행사 종료일 (yyyy.MM.dd)
 * @param imageUrl  행사 이미지
 * @param mapX      x좌표
 * @param mapY      y좌표
 * @param link      더보기 링크
 */
data class AreaEvent(
    val title: String,
    val address: String,
    val startDate: String,
    val endDate: String,
    val imageUrl: String,
    val mapX: Float,
    val mapY: Float,
    val link: String
)