package com.twolskone.bakeroad.core.remote.model.bakery

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BakeryReviewsResponse(
    @SerialName("has_next")
    val hasNext: Boolean = false,
    @SerialName("avg_rating")
    val avgRating: Float = 0f,
    @SerialName("review_count")
    val reviewCount: Int = 0,
    @SerialName("items")
    val items: List<BakeryReviewResponse> = emptyList()
)

@Serializable
data class BakeryReviewResponse(
    @SerialName("user_name")
    val userName: String = "",
    @SerialName("profile_img")
    val profileImg: String = "",
    @SerialName("is_like")
    val isLike: Boolean = false,
    @SerialName("review_id")
    val reviewId: Int = -1,
    @SerialName("review_content")
    val reviewContent: String = "",
    @SerialName("review_rating")
    val reviewRating: Float = 0f,
    @SerialName("review_like_count")
    val reviewLikeCount: Int = 0,
    @SerialName("review_created_at")
    val reviewCreatedAt: String = "",
    @SerialName("review_menus")
    val reviewMenus: List<Menu> = emptyList(),
    @SerialName("review_photos")
    val reviewPhotos: List<Photo> = emptyList()
) {

    @Serializable
    data class Menu(
        @SerialName("menu_name")
        val menuName: String = ""
    )

    @Serializable
    data class Photo(
        @SerialName("img_url")
        val imgUrl: String = ""
    )
}