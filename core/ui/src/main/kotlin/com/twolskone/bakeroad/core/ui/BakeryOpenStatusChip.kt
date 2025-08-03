package com.twolskone.bakeroad.core.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipStyle
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus

/**
 * 영업 상태 Chip
 * @param openStatus    영업 상태 (영업중, 영업종료, 휴무일, 영업전)
 */
@Composable
fun BakeryOpenStatusChip(
    modifier: Modifier = Modifier,
    openStatus: BakeryOpenStatus,
    size: ChipSize = ChipSize.SMALL,
    style: ChipStyle = ChipStyle.FILL
) {
    val color = when (openStatus) {
        BakeryOpenStatus.OPEN -> ChipColor.MAIN
        BakeryOpenStatus.CLOSED, BakeryOpenStatus.DAY_OFF, BakeryOpenStatus.BEFORE_OPEN -> ChipColor.LIGHT_GRAY
    }
    val label = stringResource(
        id = when (openStatus) {
            BakeryOpenStatus.OPEN -> R.string.core_ui_label_bakery_open_status_open
            BakeryOpenStatus.CLOSED -> R.string.core_ui_label_bakery_open_status_closed
            BakeryOpenStatus.DAY_OFF -> R.string.core_ui_label_bakery_open_status_day_off
            BakeryOpenStatus.BEFORE_OPEN -> R.string.core_ui_label_bakery_open_status_before_open
        }
    )

    BakeRoadChip(
        modifier = modifier,
        size = size,
        color = color,
        style = style,
        selected = false,
        label = { Text(text = label) }
    )
}