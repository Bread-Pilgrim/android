package com.twolskone.bakeroad.core.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.twolskone.bakeroad.core.model.type.DayOfWeek
import com.twolskone.bakeroad.core.ui.R

val DayOfWeek.toLabel: String
    @Composable
    get() = when (this) {
        DayOfWeek.MONDAY -> stringResource(R.string.core_ui_monday)
        DayOfWeek.TUESDAY -> stringResource(R.string.core_ui_tuesday)
        DayOfWeek.WEDNESDAY -> stringResource(R.string.core_ui_wednesday)
        DayOfWeek.THURSDAY -> stringResource(R.string.core_ui_thursday)
        DayOfWeek.FRIDAY -> stringResource(R.string.core_ui_friday)
        DayOfWeek.SATURDAY -> stringResource(R.string.core_ui_saturday)
        DayOfWeek.SUNDAY -> stringResource(R.string.core_ui_sunday)
    }