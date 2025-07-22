package com.twolskone.bakeroad.core.remote.model.bakery

import com.twolskone.bakeroad.core.remote.model.Paging
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BakeriesResponse(
    val paging: Paging = Paging(),
    val items: List<BakeryResponse> = emptyList()
)

@Serializable
data class BakeryResponse(
    @SerialName("bakery_id")
    val bakeryId: Int = -1,
    @SerialName("bakery_name")
    val name: String = "",
    @SerialName("commercial_area_id")
    val commercialAreaId: Int,
    @SerialName("avg_rating")
    val avgRating: Float = -1f,
    @SerialName("review_count")
    val reviewCount: Double = 0.0,
    @SerialName("open_status")
    val openStatus: String = "O",  // O : 영업중, C : 영업종료, D : 휴무일
    @SerialName("img_url")
    val imgUrl: String = "",
    @SerialName("gu")
    val gu: String = "",
    @SerialName("dong")
    val dong: String = "",
    @SerialName("signature_menus")
    val signatureMenus: List<SignatureMenu> = emptyList()
) {

    @Serializable
    data class SignatureMenu(
        @SerialName("menu_name")
        val menuName: String = ""
    )
}