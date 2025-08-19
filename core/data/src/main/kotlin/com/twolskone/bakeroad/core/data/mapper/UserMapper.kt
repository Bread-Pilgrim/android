package com.twolskone.bakeroad.core.data.mapper

import com.twolskone.bakeroad.core.model.MyBakeryReview
import com.twolskone.bakeroad.core.remote.BuildConfig
import com.twolskone.bakeroad.core.remote.model.user.MyBakeryReviewResponse

internal fun MyBakeryReviewResponse.toExternalModel(): MyBakeryReview =
    MyBakeryReview(
        id = reviewId,
        bakeryId = bakeryId,
        bakeryName = bakeryName,
        isLike = isLike,
        content = reviewContent,
        rating = reviewRating,
        likeCount = reviewLikeCount,
        menus = reviewMenus.map { it.menuName },
        photos = reviewPhotos.map { "${BuildConfig.UPLOADED_IMAGE_URL}${it.imgUrl}" }
    )