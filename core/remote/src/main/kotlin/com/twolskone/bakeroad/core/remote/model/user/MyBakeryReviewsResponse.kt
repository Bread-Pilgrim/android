package com.twolskone.bakeroad.core.remote.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyBakeryReviewsResponse(
    @SerialName("next_cursor")
    val nextCursor: String = "",
    @SerialName("items")
    val items: List<MyBakeryReviewResponse> = emptyList()
)

@Serializable
data class MyBakeryReviewResponse(
    @SerialName("review_id")
    val reviewId: Int = -1,
    @SerialName("bakery_id")
    val bakeryId: Int = -1,
    @SerialName("bakery_name")
    val bakeryName: String = "",
    @SerialName("review_content")
    val reviewContent: String = "",
    @SerialName("review_rating")
    val reviewRating: Float = 0f,
    @SerialName("review_like_count")
    val reviewLikeCount: Int = 0,
    @SerialName("is_like")
    val isLike: Boolean = false,
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