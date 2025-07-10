package com.twolskone.bakeroad.core.data.mapper

import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.core.remote.model.tour.TourAreaResponse

internal fun TourAreaResponse.toExternalModel(): TourArea =
    TourArea(
        title = title,
        type = tourType,
        address = address,
        imagePath = tourImg,
        mapX = mapX,
        mapY = mapY
    )