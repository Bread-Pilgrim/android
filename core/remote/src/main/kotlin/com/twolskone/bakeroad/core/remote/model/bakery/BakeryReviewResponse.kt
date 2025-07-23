package com.twolskone.bakeroad.core.remote.model.bakery

import com.twolskone.bakeroad.core.remote.model.Paging
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BakeryReviewsResponse(
    @SerialName("paging")
    val paging: Paging = Paging(),
    @SerialName("items")
    val items: List<BakeryReviewResponse> = emptyList()
)

@Serializable
data class BakeryReviewResponse(
    @SerialName("avg_rating")
    val avgRating: Float = 0f,
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