package com.twolskone.bakeroad.core.model

/**
 * 관광지
 * @param title     관광지명
 * @param type      관광지 타입
 * @param address   주소
 * @param imagePath 이미지
 * @param mapX      x좌표
 * @param mapY      y좌표
 */
data class TourArea(
    val title: String,
    val type: String,
    val address: String,
    val imagePath: String,
    val mapX: Float,
    val mapY: Float
)