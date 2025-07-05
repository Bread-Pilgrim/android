package com.twolskone.bakeroad.core.designsystem.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

/**
 * Outlined button with generic content slot.
 */
@Composable
fun BakeRoadOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: OutlinedButtonStyle,
    size: ButtonSize,
    content: @Composable () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = style.colors,
        shape = size.shape,
        border = BorderStroke(width = 1.dp, color = style.outlineColor(enabled = enabled)),
        contentPadding = size.contentPadding
    ) {
        ProvideTextStyle(value = size.typography, content)
    }
}

/**
 * Outlined button with text and icon content slots.
 */
@Composable
fun BakeRoadOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: OutlinedButtonStyle,
    size: ButtonSize,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?
) {
    BakeRoadOutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        style = style,
        size = size
    ) {
        ProvideTextStyle(value = size.typography) {
            BakeRoadButtonContent(
                text = text,
                buttonSize = size,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon
            )
        }
    }
}

enum class OutlinedButtonStyle {
    PRIMARY,
    SECONDARY,
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

            SECONDARY -> ButtonColors(
                containerColor = BakeRoadTheme.colorScheme.White,
                contentColor = BakeRoadTheme.colorScheme.Primary500,
                disabledContainerColor = BakeRoadTheme.colorScheme.White,
                disabledContentColor = BakeRoadTheme.colorScheme.Gray200
            )

            ASSISTIVE -> ButtonColors(
                containerColor = BakeRoadTheme.colorScheme.White,
                contentColor = BakeRoadTheme.colorScheme.Gray990,
                disabledContainerColor = BakeRoadTheme.colorScheme.White,
                disabledContentColor = BakeRoadTheme.colorScheme.Gray200
            )
        }

    @Composable
    fun outlineColor(enabled: Boolean) =
        when (this) {
            PRIMARY -> if (enabled) BakeRoadTheme.colorScheme.Primary500 else BakeRoadTheme.colorScheme.Gray200
            SECONDARY, ASSISTIVE -> BakeRoadTheme.colorScheme.Gray200
        }
}