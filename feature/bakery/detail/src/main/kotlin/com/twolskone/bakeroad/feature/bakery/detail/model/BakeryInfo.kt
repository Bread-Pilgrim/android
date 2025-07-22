package com.twolskone.bakeroad.feature.bakery.detail.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus
import com.twolskone.bakeroad.core.model.type.DayOfWeek
import com.twolskone.bakeroad.feature.bakery.detail.component.toLabel

@Immutable
internal data class BakeryInfo(
    val name: String,
    val rating: Float,
    val reviewCount: Int,
    val address: String,
    val phone: String,
    val openStatus: BakeryOpenStatus,
    val openingHour: List<OpeningHour>,
    val dayOff: List<DayOfWeek>,
    val isLike: Boolean,
) {

    @Immutable
    internal data class OpeningHour(
        val dayOffWeek: DayOfWeek,
        val openTime: String,
        val closeTime: String
    )
}

internal fun BakeryDetail.toBakeryInfo(): BakeryInfo =
    BakeryInfo(
        name = name,
        rating = rating,
        reviewCount = reviewCount,
        address = address,
        phone = phone,
        openStatus = openStatus,
        openingHour = openingHours
            .filter { it.isOpened }
            .map {
                BakeryInfo.OpeningHour(
                    dayOffWeek = it.dayOfWeek,
                    openTime = it.openTime,
                    closeTime = it.closeTime
                )
            },
        dayOff = openingHours
            .filter { !it.isOpened }
            .map { it.dayOfWeek },
        isLike = isLike
    )

internal val BakeryInfo.OpeningHour.openingHourLabel: String
    @Composable
    get() = "${dayOffWeek.toLabel} $openTime ~ $closeTime"