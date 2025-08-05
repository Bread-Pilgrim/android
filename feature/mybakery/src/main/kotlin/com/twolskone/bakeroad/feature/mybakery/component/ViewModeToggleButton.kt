package com.twolskone.bakeroad.feature.mybakery.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.twolskone.bakeroad.core.designsystem.extension.noRippleSingleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.mybakery.model.ViewMode

private val BackgroundShape = RoundedCornerShape(8.dp)
private val ToggleButtonShape = RoundedCornerShape(6.dp)

@Composable
internal fun ViewModeToggleButton(
    modifier: Modifier = Modifier,
    selectedViewMode: ViewMode,
    colors: ViewModeToggleButtonColors = ViewModeToggleButtonDefaults.colors,
    onClick: (ViewMode) -> Unit
) {
    Row(
        modifier = modifier
            .background(color = colors.backgroundColor, shape = BackgroundShape)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ViewMode.entries.fastForEach { viewMode ->
            val selected = (selectedViewMode == viewMode)
            Box(
                modifier = Modifier
                    .noRippleSingleClickable { onClick(viewMode) }
                    .background(color = colors.containerColor(selected = selected), shape = ToggleButtonShape)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = viewMode.iconRes),
                    contentDescription = viewMode.contentDescription,
                    tint = colors.contentColor(selected = selected)
                )
            }
        }
    }
}

internal object ViewModeToggleButtonDefaults {

    val colors: ViewModeToggleButtonColors
        @Composable
        get() = ViewModeToggleButtonColors(
            backgroundColor = BakeRoadTheme.colorScheme.Gray50,
            selectedContainerColor = BakeRoadTheme.colorScheme.Secondary600,
            selectedContentColor = BakeRoadTheme.colorScheme.White,
            unselectedContainerColor = BakeRoadTheme.colorScheme.Gray50,
            unselectedContentColor = BakeRoadTheme.colorScheme.Gray200
        )
}

@Immutable
internal data class ViewModeToggleButtonColors(
    val backgroundColor: Color,
    val selectedContainerColor: Color,
    val selectedContentColor: Color,
    val unselectedContainerColor: Color,
    val unselectedContentColor: Color
) {

    @Composable
    fun containerColor(selected: Boolean) = if (selected) selectedContainerColor else unselectedContainerColor

    @Composable
    fun contentColor(selected: Boolean) = if (selected) selectedContentColor else unselectedContentColor
}

@Preview
@Composable
private fun ViewModeToggleButtonPreview() {
    BakeRoadTheme {
        ViewModeToggleButton(
            selectedViewMode = ViewMode.LIST,
            onClick = {}
        )
    }
}