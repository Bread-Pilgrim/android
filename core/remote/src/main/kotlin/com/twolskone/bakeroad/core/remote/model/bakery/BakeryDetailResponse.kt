package com.twolskone.bakeroad.core.remote.model.bakery

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BakeryDetailResponse(
    @SerialName("bakery_id")
    val bakeryId: Int = -1,
    @SerialName("bakery_name")
    val bakeryName: String = "",
    @SerialName("address")
    val address: String = "",
    @SerialName("phone")
    val phone: String = "",
    @SerialName("avg_rating")
    val avgRating: Float = -1f,
    @SerialName("review_count")
    val reviewCount: Int = 0,
    @SerialName("open_status")
    val openStatus: String,
    @SerialName("operating_hours")
    val operatingHours: List<OperatingHour>,
    @SerialName("is_like")
    val isLike: Boolean = false,
    @SerialName("bakery_img_urls")
    val bakeryImgUrls: List<String> = emptyList(),
    @SerialName("menus")
    val menus: List<Menu> = emptyList()

) {

    @Serializable
    data class OperatingHour(
        @SerialName("day_of_week")
        val dayOfWeek: Int = 0,     // 0: 월
        @SerialName("open_time")
        val openTime: String = "",
        @SerialName("close_time")
        val closeTime: String = "",
        @SerialName("is_opened")
        val isOpened: Boolean = false
    )

    @Serializable
    data class Menu(
        @SerialName("menu_name")
        val menuName: String = "",
        @SerialName("price")
        val price: Int = 0,
        @SerialName("is_signature")
        val isSignature: Boolean = false,
        @SerialName("img_url")
        val imgUrl: String = ""
    )
}