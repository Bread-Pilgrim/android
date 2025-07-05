package com.twolskone.bakeroad.core.designsystem.component.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

/**
 * Text button.
 */
@Composable
fun BakeRoadTextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: TextButtonStyle,
    size: TextButtonSize,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    TextButton(
        modifier = modifier,
        shape = RoundedCornerShape(5.dp),
        enabled = enabled,
        colors = style.colors,
        contentPadding = size.contentPadding,
        onClick = onClick
    ) {
        ProvideTextStyle(value = size.typography, content = content)
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
            ProvideTextStyle(value = size.typography) {
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

    val colors: ButtonColors
        @Composable
        get() = when (this) {
            PRIMARY ->
                ButtonColors(
                    containerColor = BakeRoadTheme.colorScheme.White,
                    contentColor = BakeRoadTheme.colorScheme.Primary500,
                    disabledContainerColor = BakeRoadTheme.colorScheme.White,
                    disabledContentColor = BakeRoadTheme.colorScheme.Gray200
                )

            ASSISTIVE -> ButtonColors(
                containerColor = BakeRoadTheme.colorScheme.White,
                contentColor = BakeRoadTheme.colorScheme.Gray800,
                disabledContainerColor = BakeRoadTheme.colorScheme.White,
                disabledContentColor = BakeRoadTheme.colorScheme.Gray200
            )
        }
}