package com.twolskone.bakeroad.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

/**
 * Button common size
 */
enum class ButtonSize {
    XLARGE,
    LARGE,
    MEDIUM,
    SMALL,
    XSMALL;

    val shape: RoundedCornerShape
        @Composable
        get() = when (this) {
            XLARGE -> RoundedCornerShape(12.dp)
            LARGE -> RoundedCornerShape(10.dp)
            MEDIUM -> RoundedCornerShape(8.dp)
            SMALL -> RoundedCornerShape(6.dp)
            XSMALL -> RoundedCornerShape(4.dp)
        }

    val contentPadding: PaddingValues
        @Composable
        get() = when (this) {
            XLARGE -> PaddingValues(horizontal = 36.dp, vertical = 16.dp)
            LARGE -> PaddingValues(horizontal = 28.dp, vertical = 13.dp)
            MEDIUM -> PaddingValues(horizontal = 20.dp, vertical = 9.dp)
            SMALL -> PaddingValues(horizontal = 14.dp, vertical = 7.dp)
            XSMALL -> PaddingValues(horizontal = 12.dp, vertical = 5.dp)
        }

    val typography: TextStyle
        @Composable
        get() = when (this) {
            XLARGE -> BakeRoadTheme.typography.bodyLargeSemibold
            LARGE -> BakeRoadTheme.typography.bodyMediumSemibold
            MEDIUM -> BakeRoadTheme.typography.bodySmallSemibold
            SMALL -> BakeRoadTheme.typography.body2XSmallSemibold
            XSMALL -> BakeRoadTheme.typography.body2XSmallMedium
        }

    val iconSize: Dp
        @Composable
        get() = when (this) {
            XLARGE, LARGE -> 20.dp
            MEDIUM -> 18.dp
            SMALL, XSMALL -> 16.dp
        }

    val iconSpacing: Dp
        @Composable
        get() = when (this) {
            XLARGE, LARGE -> 6.dp
            MEDIUM -> 5.dp
            SMALL, XSMALL -> 4.dp
        }
}

/**
 * Button content layout for arranging the text label, leading icon, and trailing icon.
 */
@Composable
internal fun BakeRoadSolidButtonContent(
    buttonSize: ButtonSize,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    if (leadingIcon != null) {
        Box(modifier = Modifier.size(buttonSize.iconSize)) {
            leadingIcon()
        }
    }
    Box(
        modifier = Modifier
            .padding(
                start = if (leadingIcon != null) buttonSize.iconSpacing else 0.dp,
                end = if (trailingIcon != null) buttonSize.iconSpacing else 0.dp,
            )
    ) {
        text()
    }
    if (trailingIcon != null) {
        Box(modifier = Modifier.size(buttonSize.iconSize)) {
            trailingIcon()
        }
    }
}