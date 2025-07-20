package com.twolskone.bakeroad.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
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
    content: @Composable RowScope.() -> Unit
) {
    val containerColor = if (enabled) style.colors.containerColor else style.colors.disabledContainerColor
    val contentColor = if (enabled) style.colors.contentColor else style.colors.contentColor

    Box(
        modifier = modifier
            .background(color = containerColor, shape = size.shape)
            .clip(size.shape)
            .singleClickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        ProvideContentColorTextStyle(
            contentColor = contentColor,
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
        BakeRoadButtonContent(
            text = text,
            buttonSize = size,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon
        )
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