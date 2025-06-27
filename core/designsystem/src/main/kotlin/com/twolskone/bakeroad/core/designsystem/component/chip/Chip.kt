package com.twolskone.bakeroad.core.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

enum class ChipColor {
    MAIN, SUB, GRAY, LIGHT_GRAY, RED, SUCCESS;

    @Composable
    fun getContainerColor(style: ChipStyle): Color =
        when (this) {
            MAIN -> if (style == ChipStyle.WEAK) BakeRoadTheme.colorScheme.Primary50 else BakeRoadTheme.colorScheme.Primary500
            SUB -> if (style == ChipStyle.WEAK) BakeRoadTheme.colorScheme.Secondary100 else BakeRoadTheme.colorScheme.Secondary500
            GRAY -> if (style == ChipStyle.WEAK) BakeRoadTheme.colorScheme.Gray200 else BakeRoadTheme.colorScheme.Gray800
            LIGHT_GRAY -> if (style == ChipStyle.WEAK) BakeRoadTheme.colorScheme.Gray50 else BakeRoadTheme.colorScheme.Gray400
            RED -> if (style == ChipStyle.WEAK) BakeRoadTheme.colorScheme.Error100 else BakeRoadTheme.colorScheme.Error500
            SUCCESS -> if (style == ChipStyle.WEAK) BakeRoadTheme.colorScheme.Success100 else BakeRoadTheme.colorScheme.Success500
        }

    @Composable
    fun getContentColor(style: ChipStyle): Color =
        when (this) {
            MAIN -> if (style == ChipStyle.WEAK) BakeRoadTheme.colorScheme.Primary500 else BakeRoadTheme.colorScheme.White
            SUB -> if (style == ChipStyle.WEAK) BakeRoadTheme.colorScheme.Secondary500 else BakeRoadTheme.colorScheme.White
            GRAY -> if (style == ChipStyle.WEAK) BakeRoadTheme.colorScheme.Gray800 else BakeRoadTheme.colorScheme.White
            LIGHT_GRAY -> if (style == ChipStyle.WEAK) BakeRoadTheme.colorScheme.Gray400 else BakeRoadTheme.colorScheme.White
            RED -> if (style == ChipStyle.WEAK) BakeRoadTheme.colorScheme.Error500 else BakeRoadTheme.colorScheme.White
            SUCCESS -> if (style == ChipStyle.WEAK) BakeRoadTheme.colorScheme.Success500 else BakeRoadTheme.colorScheme.White
        }
}

enum class ChipSize {
    SMALL, LARGE;

    val typography: TextStyle
        @Composable
        get() = when (this) {
            SMALL -> BakeRoadTheme.typography.body2XsmallMedium
            LARGE -> BakeRoadTheme.typography.bodyXsmallMedium
        }

    val padding: PaddingValues
        @Composable
        get() = when (this) {
            SMALL -> PaddingValues(horizontal = 8.dp, vertical = 3.dp)
            LARGE -> PaddingValues(horizontal = 10.dp, vertical = 5.dp)
        }

    val shape: RoundedCornerShape
        @Composable
        get() = when (this) {
            SMALL -> RoundedCornerShape(6.dp)
            LARGE -> RoundedCornerShape(8.dp)
        }
}

enum class ChipStyle {
    WEAK, FILL
}

@Composable
fun BakeRoadChip(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    color: ChipColor = ChipColor.MAIN,
    size: ChipSize = ChipSize.LARGE,
    label: @Composable () -> Unit,
) {
    val style = if (selected) ChipStyle.FILL else ChipStyle.WEAK
    
    Box(
        modifier = modifier
            .clickable { onSelectedChange(!selected) }
            .background(color = color.getContainerColor(style), shape = size.shape)
            .padding(size.padding),
        contentAlignment = Alignment.Center
    ) {
        ProvideTextStyle(value = size.typography.copy(color = color.getContentColor(style))) {
            label()
        }
    }
}

@Preview
@Composable
private fun BakeRoadChipPreview() {
    BakeRoadTheme {
        var selected by remember { mutableStateOf(false) }

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            BakeRoadChip(
                selected = selected,
                onSelectedChange = { selected = it },
                label = { Text(text = "Chip") },
                size = ChipSize.SMALL
            )
            BakeRoadChip(
                selected = selected,
                onSelectedChange = { selected = it },
                label = { Text(text = "Chip") }
            )
        }
    }
}