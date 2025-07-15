package com.twolskone.bakeroad.core.data.mapper

import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.RecommendBakery
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryResponse
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse

internal fun RecommendBakeryResponse.toExternalModel(): RecommendBakery =
    RecommendBakery(
        id = bakeryId,
        name = name,
        rating = avgRating,
        reviewCount = reviewCount.toInt(),
        openStatus = runCatching {
            BakeryOpenStatus.valueOf(openStatus)
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
            BakeryOpenStatus.valueOf(openStatus)
        }.getOrDefault(BakeryOpenStatus.OPEN),
        imageUrl = imgUrl,
        addressGu = gu,
        addressDong = dong,
        signatureMenus = signatureMenus.map { it.menuName }
    )