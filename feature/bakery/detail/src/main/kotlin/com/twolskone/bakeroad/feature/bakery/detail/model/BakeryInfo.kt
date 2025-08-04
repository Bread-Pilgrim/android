package com.twolskone.bakeroad.feature.bakery.detail.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus
import com.twolskone.bakeroad.core.model.type.DayOfWeek
import com.twolskone.bakeroad.feature.bakery.detail.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Immutable
internal data class BakeryInfo(
    val name: String,
    val address: String,
    val phone: String,
    val openStatus: BakeryOpenStatus,
    val openingHour: ImmutableList<OpeningHour>,
    val dayOff: ImmutableList<DayOfWeek>,
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
            }.toImmutableList(),
        dayOff = openingHours
            .filter { !it.isOpened }
            .map { it.dayOfWeek }
            .toImmutableList(),
        isLike = isLike
    )

internal val BakeryInfo.OpeningHour.openingHourLabel: String
    @Composable
    get() = "${dayOffWeek.toLabel} $openTime ~ $closeTime"

private val DayOfWeek.toLabel: String
    @Composable
    get() = when (this) {
        DayOfWeek.MONDAY -> stringResource(R.string.feature_bakery_detail_monday)
        DayOfWeek.TUESDAY -> stringResource(R.string.feature_bakery_detail_tuesday)
        DayOfWeek.WEDNESDAY -> stringResource(R.string.feature_bakery_detail_wednesday)
        DayOfWeek.THURSDAY -> stringResource(R.string.feature_bakery_detail_thursday)
        DayOfWeek.FRIDAY -> stringResource(R.string.feature_bakery_detail_friday)
        DayOfWeek.SATURDAY -> stringResource(R.string.feature_bakery_detail_saturday)
        DayOfWeek.SUNDAY -> stringResource(R.string.feature_bakery_detail_sunday)
    }