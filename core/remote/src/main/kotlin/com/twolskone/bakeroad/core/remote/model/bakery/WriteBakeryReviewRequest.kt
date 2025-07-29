package com.twolskone.bakeroad.core.remote.model.bakery

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WriteBakeryReviewRequest(
    @SerialName("rating")
    val rating: Float,
    @SerialName("content")
    val content: String,
    @SerialName("is_private")
    val isPrivate: Boolean,
    @SerialName("consumed_menus")
    val consumedMenus: List<Menu>,
    @SerialName("review_imgs")
    val reviewImgs: List<String>
) {

    @Serializable
    data class Menu(
        @SerialName("menu_id")
        val menuId: Int,
        @SerialName("quantity")
        val quantity: Int
    )
}