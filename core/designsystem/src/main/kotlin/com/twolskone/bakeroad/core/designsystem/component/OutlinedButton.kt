package com.twolskone.bakeroad.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

enum class OutlinedButtonRole {
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

/**
 * Outlined button with generic content slot.
 */
@Composable
fun BakeRoadOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    role: OutlinedButtonRole,
    size: ButtonSize,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = role.colors,
        shape = size.shape,
        border = BorderStroke(width = 1.dp, color = role.outlineColor(enabled = enabled)),
        contentPadding = size.contentPadding
    ) {
        CompositionLocalProvider(LocalTextStyle provides size.typography) {
            content()
        }
    }
}

/**
 * Outlined button with text and icon content slots
 */
@Composable
fun BakeRoadOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    role: OutlinedButtonRole,
    size: ButtonSize,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?
) {
    BakeRoadOutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        role = role,
        size = size
    ) {
        CompositionLocalProvider(LocalTextStyle provides size.typography) {
            BakeRoadSolidButtonContent(
                text = text,
                buttonSize = size,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon
            )
        }
    }
}