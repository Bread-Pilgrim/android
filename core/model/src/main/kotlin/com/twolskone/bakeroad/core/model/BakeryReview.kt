package com.twolskone.bakeroad.core.model

/**
 * 리뷰
 * @param id            리뷰 ID
 * @param avgRating     평균 별점
 * @param profileUrl    유저 프로필 이미지
 * @param isLike        사용자 좋아요 여부
 * @param content       리뷰 내용
 * @param rating        별점
 * @param likeCount     좋아요 개수
 * @param menus         리뷰한 메뉴
 * @param photos        리뷰 사진
 */
data class BakeryReview(
    val id: Int,
    val avgRating: Float,
    val userName: String,
    val profileUrl: String,
    val isLike: Boolean,
    val content: String,
    val rating: Float,
    val likeCount: Int,
    val menus: List<String>,
    val photos: List<String>
)