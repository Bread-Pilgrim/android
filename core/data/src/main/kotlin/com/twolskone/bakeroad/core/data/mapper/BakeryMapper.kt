package com.twolskone.bakeroad.core.data.mapper

import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.core.model.RecommendBakery
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryDetailResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryResponse
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse

internal fun RecommendBakeryResponse.toExternalModel(): RecommendBakery =
    RecommendBakery(
        id = bakeryId,
        name = name,
        rating = avgRating,
        reviewCount = reviewCount.toInt(),
        openStatus = runCatching {
            BakeryOpenStatus.ofStatus(openStatus) ?: BakeryOpenStatus.OPEN
        }.getOrDefault(BakeryOpenStatus.OPEN),
        imageUrl = imgUrl
    )

internal fun BakeryResponse.toExternalModel(): Bakery =
    Bakery(
        id = bakeryId,
        name = name,
        rating = avgRating,
        reviewCount = reviewCount.toInt(),
        openStatus = runCatching {
            BakeryOpenStatus.ofStatus(openStatus) ?: BakeryOpenStatus.OPEN
        }.getOrDefault(BakeryOpenStatus.OPEN),
        imageUrl = imgUrl,
        addressGu = gu,
        addressDong = dong,
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