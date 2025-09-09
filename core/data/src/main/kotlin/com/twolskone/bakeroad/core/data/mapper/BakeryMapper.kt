package com.twolskone.bakeroad.core.data.mapper

import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.RecommendBakery
import com.twolskone.bakeroad.core.model.ReviewMenu
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus
import com.twolskone.bakeroad.core.model.type.DayOfWeek
import com.twolskone.bakeroad.core.remote.BuildConfig
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryDetailResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryMenuResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryReviewResponse
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal fun RecommendBakeryResponse.toExternalModel(): RecommendBakery =
    RecommendBakery(
        id = bakeryId,
        name = bakeryName,
        areaCode = commercialAreaId,
        rating = avgRating,
        reviewCount = reviewCount.toInt(),
        openStatus = runCatching {
            BakeryOpenStatus.ofStatus(openStatus) ?: BakeryOpenStatus.OPEN
        }.getOrDefault(BakeryOpenStatus.OPEN),
        imageUrl = imgUrl,
        isLike = isLike
    )

internal fun BakeryResponse.toExternalModel(): Bakery =
    Bakery(
        id = bakeryId,
        name = bakeryName,
        areaCode = commercialAreaId,
        rating = avgRating,
        reviewCount = reviewCount.toInt(),
        openStatus = runCatching {
            BakeryOpenStatus.ofStatus(openStatus) ?: BakeryOpenStatus.OPEN
        }.getOrDefault(BakeryOpenStatus.OPEN),
        imageUrl = imgUrl,
        addressGu = gu,
        addressDong = dong,
        isLike = isLike,
        signatureMenus = signatureMenus.map { it.menuName }
    )

internal fun BakeryDetailResponse.toExternalModel(): BakeryDetail =
    BakeryDetail(
        id = bakeryId,
        name = bakeryName,
        address = address,
        phone = phone,
        openStatus = runCatching {
            BakeryOpenStatus.ofStatus(openStatus) ?: BakeryOpenStatus.OPEN
        }.getOrDefault(BakeryOpenStatus.OPEN),
        isLike = isLike,
        mapX = mapx,
        mapY = mapy,
        openingHours = operatingHours
            .sortedBy { it.dayOfWeek }
            .map {
                BakeryDetail.OpeningHour(
                    dayOfWeek = DayOfWeek.ofValue(it.dayOfWeek) ?: DayOfWeek.MONDAY,
                    openTime = it.openTime,
                    closeTime = it.closeTime,
                    isOpened = it.isOpened
                )
            },
        imageUrls = bakeryImgUrls,
        menus = menus.map { menu ->
            BakeryDetail.Menu(
                name = menu.menuName,
                price = menu.price,
                isSignature = menu.isSignature,
                imageUrl = menu.imgUrl
            )
        }
    )

internal fun BakeryReviewResponse.toExternalModel(avgRating: Float, reviewCount: Int): BakeryReview =
    BakeryReview(
        id = reviewId,
        avgRating = avgRating,
        totalCount = reviewCount,
        userName = userName,
        profileUrl = profileImg,
        isLike = isLike,
        content = reviewContent,
        rating = reviewRating,
        likeCount = reviewLikeCount,
        date = runCatching { LocalDate.parse(reviewCreatedAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME) }.getOrDefault(null),
        menus = reviewMenus.map { it.menuName },
        photos = reviewPhotos.map { "${BuildConfig.UPLOADED_IMAGE_URL}${it.imgUrl}" }
    )

internal fun BakeryMenuResponse.toReviewMenu(): ReviewMenu =
    ReviewMenu(
        id = menuId,
        name = menuName,
        breadTypeId = breadTypeId,
        isSignature = isSignature,
        count = 0
    )