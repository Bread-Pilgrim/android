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
        name = name,
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
        rating = avgRating,
        reviewCount = reviewCount,
        openStatus = runCatching {
            BakeryOpenStatus.ofStatus(openStatus) ?: BakeryOpenStatus.OPEN
        }.getOrDefault(BakeryOpenStatus.OPEN),
        isLike = isLike,
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

internal fun BakeryReviewResponse.toExternalModel(): BakeryReview =
    BakeryReview(
        id = reviewId,
        avgRating = avgRating,
        userName = userName,
        profileUrl = profileImg,
        isLike = isLike,
        content = reviewContent,
        rating = reviewRating,
        likeCount = reviewLikeCount,
        menus = reviewMenus.map { it.menuName },
        photos = reviewPhotos.map { "${BuildConfig.UPLOADED_IMAGE_URL}${it.imgUrl}" }
    )

internal fun BakeryMenuResponse.toReviewMenu(): ReviewMenu =
    ReviewMenu(
        id = menuId,
        name = menuName,
        isSignature = isSignature,
        count = 0
    )