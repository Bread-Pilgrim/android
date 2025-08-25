package com.twolskone.bakeroad.core.data.mapper

import com.twolskone.bakeroad.core.model.Badge
import com.twolskone.bakeroad.core.remote.model.badge.BadgeResponse

internal fun BadgeResponse.toExternalModel(): Badge =
    Badge(
        id = badgeId,
        name = badgeName,
        description = description,
        imageUrl = imgUrl,
        isEarned = isEarned,
        isRepresentative = isRepresentative
    )