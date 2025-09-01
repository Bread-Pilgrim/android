package com.twolskone.bakeroad.core.data.mapper

import com.twolskone.bakeroad.core.model.Badge
import com.twolskone.bakeroad.core.remote.BuildConfig
import com.twolskone.bakeroad.core.remote.model.badge.BadgeResponse

internal fun BadgeResponse.toExternalModel(): Badge =
    Badge(
        id = badgeId,
        name = badgeName,
        description = description,
        imageUrl = "${BuildConfig.UPLOADED_IMAGE_URL}$imgUrl",
        isEarned = isEarned,
        isRepresentative = isRepresentative
    )