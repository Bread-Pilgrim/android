package com.twolskone.bakeroad.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

private val Shape = RoundedCornerShape(5.dp)

/**
 * Text button
 */
@Composable
fun BakeRoadTextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: TextButtonStyle,
    size: TextButtonSize,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .clip(Shape)
            .singleClickable { onClick() }
            .background(
                color = style.getContainerColor(enabled = enabled),
                shape = Shape
            ),
        contentAlignment = Alignment.Center
    ) {
        ProvideContentColorTextStyle(
            contentColor = style.getContentColor(enabled = enabled),
            textStyle = size.typography
        ) {
            Row(
                modifier = Modifier.padding(size.contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

/**
 * Text button with icon content slots.
 */
@Composable
fun BakeRoadTextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: TextButtonStyle,
    size: TextButtonSize,
    onClick: () -> Unit,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    BakeRoadTextButton(
        modifier = modifier,
        enabled = enabled,
        style = style,
        size = size,
        onClick = onClick,
        content = {
            if (leadingIcon != null) {
                Box(
                    modifier = Modifier.size(size.iconSize),
                    contentAlignment = Alignment.Center,
                ) {
                    leadingIcon()
                }
            }
            Box(
                modifier = Modifier
                    .padding(
                        start = if (leadingIcon != null) 4.dp else 0.dp,
                        end = if (trailingIcon != null) 4.dp else 0.dp,
                    )
            ) {
                text()
            }
            if (trailingIcon != null) {
                Box(
                    modifier = Modifier.size(size.iconSize),
                    contentAlignment = Alignment.Center
                ) {
                    trailingIcon()
                }
            }
        }
    )
}

enum class TextButtonSize {
    MEDIUM,
    SMALL;

    val typography: TextStyle
        @Composable
        get() = when (this) {
            MEDIUM -> BakeRoadTheme.typography.bodyMediumSemibold
            SMALL -> BakeRoadTheme.typography.bodySmallSemibold
        }

    val contentPadding: PaddingValues
        @Composable
        get() = when (this) {
            MEDIUM -> PaddingValues(horizontal = 7.dp, vertical = 4.dp)
            SMALL -> PaddingValues(horizontal = 6.dp, vertical = 4.dp)
        }

    val iconSize: Dp
        @Composable
        get() = when (this) {
            MEDIUM -> 20.dp
            SMALL -> 16.dp
        }
}

enum class TextButtonStyle {
    PRIMARY,
    ASSISTIVE;

    @Composable
    fun getContainerColor(enabled: Boolean): Color =
        when (this) {
            PRIMARY -> if (enabled) Color.Transparent else BakeRoadTheme.colorScheme.White
            ASSISTIVE -> Color.Transparent
        }

    @Composable
    fun getContentColor(enabled: Boolean): Color =
        when (this) {
            PRIMARY -> if (enabled) BakeRoadTheme.colorScheme.Primary500 else BakeRoadTheme.colorScheme.Gray200
            ASSISTIVE -> if (enabled) BakeRoadTheme.colorScheme.Gray800 else BakeRoadTheme.colorScheme.Gray200
        }
}