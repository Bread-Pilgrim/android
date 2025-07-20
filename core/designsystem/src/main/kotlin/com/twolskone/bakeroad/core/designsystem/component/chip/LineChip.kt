package com.twolskone.bakeroad.core.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.extension.INTERVAL_SINGLE_CLICK
import com.twolskone.bakeroad.core.designsystem.extension.noRippleSingleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

/**
 * A chip component with border.
 * TODO. Chip.kt 와 같이 디자인 시스템 내 SIZE 및 COLOR 에 대한 정의 시 토큰화 필요.
 */
@Composable
fun BakeRoadLineChip(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit = {},
    selectInterval: Long = INTERVAL_SINGLE_CLICK,
    label: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .noRippleSingleClickable(clickInterval = selectInterval) { onSelectedChange(!selected) }
            .background(color = containerColor, shape = CircleShape)
            .border(
                width = 1.dp,
                color = getLineColor(selected = selected),
                shape = CircleShape
            )
            .padding(horizontal = 11.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        ProvideTextStyle(
            value = getTypography(selected = selected)
                .copy(color = getContentColor(selected = selected))
        ) {
            label()
        }
    }
}

private val containerColor: Color
    @Composable
    get() = Color.Transparent

@Composable
private fun getContentColor(selected: Boolean): Color =
    if (selected) {
        BakeRoadTheme.colorScheme.Primary500
    } else {
        BakeRoadTheme.colorScheme.Gray200
    }

@Composable
private fun getLineColor(selected: Boolean): Color =
    if (selected) {
        BakeRoadTheme.colorScheme.Primary500
    } else {
        BakeRoadTheme.colorScheme.Gray100
    }

@Composable
private fun getTypography(selected: Boolean): TextStyle =
    if (selected) {
        BakeRoadTheme.typography.body2XsmallSemibold
    } else {
        BakeRoadTheme.typography.body2XsmallMedium
    }

@Preview(showBackground = true)
@Composable
private fun BakeRoadLineChipPreview() {
    BakeRoadTheme {
        BakeRoadLineChip(
            selected = true,
            label = { Text(text = "Chip") }
        )
    }
}