package com.twolskone.bakeroad.feature.bakery.detail.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus
import com.twolskone.bakeroad.core.model.type.DayOfWeek
import com.twolskone.bakeroad.core.ui.extension.toLabel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Immutable
internal data class BakeryInfo(
    val name: String,
    val address: String,
    val phone: String,
    val openStatus: BakeryOpenStatus,
    val isLike: Boolean,
    val mapX: Float,
    val mapY: Float,
    val openingHour: ImmutableList<OpeningHour>,
    val dayOff: ImmutableList<DayOfWeek>
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
        address = address,
        phone = phone,
        openStatus = openStatus,
        isLike = isLike,
        mapX = mapX,
        mapY = mapY,
        openingHour = openingHours
            .filter { it.isOpened }
            .map {
                BakeryInfo.OpeningHour(
                    dayOffWeek = it.dayOfWeek,
                    openTime = it.openTime,
                    closeTime = it.closeTime
                )
            }.toImmutableList(),
        dayOff = openingHours
            .filter { !it.isOpened }
            .map { it.dayOfWeek }
            .toImmutableList()
    )

internal val BakeryInfo.OpeningHour.openingHourLabel: String
    @Composable
    get() = "${dayOffWeek.toLabel} $openTime ~ $closeTime"