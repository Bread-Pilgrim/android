package com.twolskone.bakeroad.core.data.mapper

import com.twolskone.bakeroad.core.model.AreaEvent
import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.core.remote.model.tour.TourAreaEventResponse
import com.twolskone.bakeroad.core.remote.model.tour.TourAreaResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import timber.log.Timber

internal fun TourAreaResponse.toExternalModel(): TourArea =
    TourArea(
        title = title,
        type = tourType,
        address = address,
        imagePath = tourImg,
        mapX = mapX,
        mapY = mapY
    )

internal fun TourAreaEventResponse.toExternalModel(): AreaEvent =
    AreaEvent(
        title = title,
        address = address,
        startDate = formatAreaEventDate(date = startDate),
        endDate = formatAreaEventDate(date = endDate),
        imageUrl = eventImg,
        mapX = mapX,
        mapY = mapY,
        link = readMoreLink
    )

/**
 * 지역 행사 날짜 포멧 설정
 * @param date  서버 응답 날짜 (yyyy-MM-dd)
 * @return      화면 표시 날짜 (yyyy.MM.dd)
 */
private fun formatAreaEventDate(date: String): String {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.getDefault())

        LocalDate.parse(date, inputFormatter).format(outputFormatter)
    } catch (e: Exception) {
        Timber.e(e)
        ""
    }
}