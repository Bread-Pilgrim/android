package com.twolskone.bakeroad.core.data.mapper

import com.twolskone.bakeroad.core.common.kotlin.extension.orZero
import com.twolskone.bakeroad.core.model.MyBakeryReview
import com.twolskone.bakeroad.core.model.Profile
import com.twolskone.bakeroad.core.model.ReportDate
import com.twolskone.bakeroad.core.model.ReportDetail
import com.twolskone.bakeroad.core.model.type.DayOfWeek
import com.twolskone.bakeroad.core.remote.BuildConfig
import com.twolskone.bakeroad.core.remote.model.user.MyBakeryReviewResponse
import com.twolskone.bakeroad.core.remote.model.user.ProfileResponse
import com.twolskone.bakeroad.core.remote.model.user.ReportMonthlyResponse
import com.twolskone.bakeroad.core.remote.model.user.ReportResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal fun MyBakeryReviewResponse.toExternalModel(): MyBakeryReview =
    MyBakeryReview(
        id = reviewId,
        bakeryId = bakeryId,
        areaCode = commercialAreaId,
        bakeryName = bakeryName,
        isLike = isLike,
        content = reviewContent,
        rating = reviewRating,
        likeCount = reviewLikeCount,
        date = runCatching { LocalDate.parse(reviewCreatedAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME) }.getOrDefault(null),
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

internal fun ReportResponse.toExternalModel(): ReportDetail = ReportDetail(
    year = year,
    month = month,
    visitedAreas = visitedAreas
        .toList()
        .sortedByDescending { it.second },
    breadTypes = breadTypes
        .toList()
        .sortedByDescending { it.second },
    breadDailyAverageCount = dailyAvgQuantity,
    breadMonthlyConsumptionGap = monthlyConsumptionGap,
    totalBreadCount = totalQuantity,
    totalVisitedCount = totalVisitCount,
    totalPrices = totalPrices,
    breadWeeklyDistribution = weeklyDistribution.mapKeys { (key, _) ->
        DayOfWeek.ofValue(key.toInt().orZero()) ?: DayOfWeek.MONDAY
    },
    reviewCount = reviewCount,
    sentLikeCount = likedCount,
    receivedLikeCount = receivedLikesCount
)