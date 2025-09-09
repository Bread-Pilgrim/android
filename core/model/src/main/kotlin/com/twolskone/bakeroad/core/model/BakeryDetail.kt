package com.twolskone.bakeroad.core.model

import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus
import com.twolskone.bakeroad.core.model.type.DayOfWeek

internal const val OtherMenu = "기타메뉴"

/**
 * 빵집 상세
 * @param id            빵집 ID
 * @param name          상호명
 * @param address       평균 평점
 * @param phone         전화번호
 * @param isLike        찜 여부
 * @param latitude      위도
 * @param longitude     경도
 * @param openingHours  요일별 영업 시간
 * @param imageUrls     썸네일 리스트
 * @param menus         메뉴
 */
data class BakeryDetail(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val openStatus: BakeryOpenStatus,
    val isLike: Boolean,
    val latitude: Float,
    val longitude: Float,
    val openingHours: List<OpeningHour>,
    val imageUrls: List<String>,
    val menus: List<Menu>
) {

    /**
     * 영업 시간
     * @param dayOfWeek 요일
     * @param openTime  오픈 시간
     * @param closeTime 종료 시간
     * @param isOpened  오픈 여부
     */
    data class OpeningHour(
        val dayOfWeek: DayOfWeek,
        val openTime: String,
        val closeTime: String,
        val isOpened: Boolean
    )

    /**
     * 빵집 메뉴
     * @param name          메뉴 이름
     * @param price         가격
     * @param isSignature   대표메뉴 여부
     * @param imageUrl      메뉴 이미지 url
     */
    data class Menu(
        val name: String,
        val price: Int,
        val isSignature: Boolean,
        val imageUrl: String
    )
}

fun BakeryDetail.Menu.isOtherMenu(): Boolean = (name == OtherMenu)  // TODO. 하드코딩으로 판별.. 맞나?