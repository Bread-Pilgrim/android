package com.twolskone.bakeroad.core.designsystem.component.button

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

/**
 * Solid button with generic content slot.
 */
@Composable
fun BakeRoadSolidButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: SolidButtonStyle,
    size: ButtonSize,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = style.colors,
        shape = size.shape,
        contentPadding = size.contentPadding
    ) {
        ProvideTextStyle(value = size.typography, content = content)
    }
}

/**
 * Solid button with text and icon content slots.
 */
@Composable
fun BakeRoadSolidButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: SolidButtonStyle,
    size: ButtonSize,
    onClick: () -> Unit,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    BakeRoadSolidButton(
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

enum class SolidButtonStyle {
    PRIMARY,
    SECONDARY;

    val colors: ButtonColors
        @Composable
        get() = when (this) {
            PRIMARY ->
                ButtonColors(
                    containerColor = BakeRoadTheme.colorScheme.Primary500,
                    contentColor = BakeRoadTheme.colorScheme.White,
                    disabledContainerColor = BakeRoadTheme.colorScheme.Gray50,
                    disabledContentColor = BakeRoadTheme.colorScheme.Gray300
                )

            SECONDARY -> ButtonColors(
                containerColor = BakeRoadTheme.colorScheme.Primary100,
                contentColor = BakeRoadTheme.colorScheme.Primary500,
                disabledContainerColor = BakeRoadTheme.colorScheme.Primary50,
                disabledContentColor = BakeRoadTheme.colorScheme.Primary200
            )
        }
}