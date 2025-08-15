package com.twolskone.bakeroad.core.datastore.mapper

import com.twolskone.bakeroad.core.model.AreaEvent
import com.twolskone.bakeroad.core.datastore.AreaEvent as AreaEventProto

internal fun AreaEventProto.toExternalModel(): AreaEvent =
    AreaEvent(
        title = title,
        address = address,
        startDate = startDate,
        endDate = endDate,
        imageUrl = imageUrl,
        mapY = 0f,  // TODO. Proto 에 추가
        mapX = 0f,  // TODO. Proto 에 추가
        link = link
    )