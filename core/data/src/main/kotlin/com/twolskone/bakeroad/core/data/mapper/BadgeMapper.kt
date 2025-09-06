package com.twolskone.bakeroad.core.data.mapper

import com.twolskone.bakeroad.core.model.Badge
import com.twolskone.bakeroad.core.remote.BuildConfig
import com.twolskone.bakeroad.core.remote.model.badge.BadgeResponse
import com.twolskone.bakeroad.core.remote.model.extra.BadgeExtraResponse

internal fun BadgeResponse.toExternalModel(): Badge =
    Badge(
        id = badgeId,
        name = badgeName,
        description = description,
        imageUrl = "${BuildConfig.UPLOADED_IMAGE_URL}$imgUrl",
        isEarned = isEarned,
        isRepresentative = isRepresentative
    )

internal fun BadgeExtraResponse.toExternalModel(): Badge =
    Badge(
        id = badgeId,
        name = badgeName,
        description = description,
        imageUrl = "${BuildConfig.UPLOADED_IMAGE_URL}$badgeImg",
        isEarned = true,
        isRepresentative = false
    )