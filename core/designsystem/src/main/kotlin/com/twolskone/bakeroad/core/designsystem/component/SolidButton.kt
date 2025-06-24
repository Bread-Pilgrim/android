package com.twolskone.bakeroad.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

enum class OutlinedButton {
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
                containerColor = BakeRoadTheme.colorScheme.Primary500,
                contentColor = BakeRoadTheme.colorScheme.White,
                disabledContainerColor = BakeRoadTheme.colorScheme.Gray50,
                disabledContentColor = BakeRoadTheme.colorScheme.Gray300
            )
        }
}

/**
 * Solid button with generic content slot.
 */
@Composable
fun BakeRoadSolidButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    role: OutlinedButton,
    size: ButtonSize,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = role.colors,
        shape = size.shape,
        contentPadding = size.contentPadding
    ) {
        CompositionLocalProvider(LocalTextStyle provides size.typography) {
            content()
        }
    }
}

/**
 * Solid button with text and icon content slots
 */
@Composable
fun BakeRoadSolidButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    role: OutlinedButton,
    size: ButtonSize,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?
) {
    BakeRoadSolidButton(
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