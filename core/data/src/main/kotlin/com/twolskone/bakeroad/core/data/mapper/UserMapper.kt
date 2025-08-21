package com.twolskone.bakeroad.core.data.mapper

import com.twolskone.bakeroad.core.model.MyBakeryReview
import com.twolskone.bakeroad.core.model.Profile
import com.twolskone.bakeroad.core.model.ReportDate
import com.twolskone.bakeroad.core.remote.BuildConfig
import com.twolskone.bakeroad.core.remote.model.user.MyBakeryReviewResponse
import com.twolskone.bakeroad.core.remote.model.user.ProfileResponse
import com.twolskone.bakeroad.core.remote.model.user.ReportMonthlyResponse

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

internal fun ProfileResponse.toExternalModel(): Profile =
    Profile(
        nickname = nickname,
        imageUrl = profileImg,
        badgeName = badgeName.takeIf { isRepresentative }.orEmpty()
    )

internal fun ReportMonthlyResponse.toExternalModel(): ReportDate = ReportDate(year = year, month = month)