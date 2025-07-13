package com.twolskone.bakeroad.core.data.mapper

import com.twolskone.bakeroad.core.model.RecommendBakery
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse

internal fun RecommendBakeryResponse.toExternalModel(): RecommendBakery =
    RecommendBakery(
        id = bakeryId,
        name = name,
        rating = avgRating,
        reviewCount = reviewCount.toInt(),
        isOpened = isOpened,
        imageUrl = imgUrl
    )