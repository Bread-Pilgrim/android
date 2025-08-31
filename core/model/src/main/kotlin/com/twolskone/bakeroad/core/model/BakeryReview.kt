package com.twolskone.bakeroad.core.model

import java.time.LocalDate

/**
 * 리뷰
 * @param id            리뷰 ID
 * @param avgRating     전체 리뷰 평균 별점
 * @param totalCount    전체 리뷰 개수
 * @param profileUrl    유저 프로필 이미지
 * @param isLike        사용자 좋아요 여부
 * @param content       리뷰 내용
 * @param rating        별점
 * @param likeCount     좋아요 개수
 * @param date          리뷰 작성 날짜
 * @param menus         리뷰한 메뉴
 * @param photos        리뷰 사진
 */
data class BakeryReview(
    val id: Int,
    val avgRating: Float,
    val totalCount: Int,
    val userName: String,
    val profileUrl: String,
    val isLike: Boolean,
    val content: String,
    val rating: Float,
    val likeCount: Int,
    val date: LocalDate?,
    val menus: List<String>,
    val photos: List<String>
)