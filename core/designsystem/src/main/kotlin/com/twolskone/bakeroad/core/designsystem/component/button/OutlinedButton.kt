package com.twolskone.bakeroad.core.designsystem.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize.LARGE
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize.MEDIUM
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize.SMALL
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize.XLARGE
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize.XSMALL
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

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
    style: ButtonStyle = ButtonStyle.DEFAULT,
    content: @Composable RowScope.() -> Unit
) {
    val containerColor = if (enabled) role.colors.containerColor else role.colors.disabledContainerColor
    val contentColor = if (enabled) role.colors.contentColor else role.colors.contentColor
    val border = BorderStroke(width = 1.dp, color = role.outlineColor(enabled = enabled))

    Box(
        modifier = modifier
            .border(border, size.shape(style = style))
            .background(color = containerColor, shape = size.shape(style = style))
            .clip(size.shape(style = style))
            .singleClickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        ProvideContentColorTextStyle(
            contentColor = contentColor,
            textStyle = role.getTypography(size)
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
 * Outlined button with text and icon content slots.
 */
@Composable
fun BakeRoadOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    role: OutlinedButtonRole,
    size: ButtonSize,
    style: ButtonStyle = ButtonStyle.DEFAULT,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    BakeRoadOutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        role = role,
        style = style,
        size = size
    ) {
        BakeRoadButtonContent(
            text = text,
            buttonSize = size,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            onParentClick = onClick
        )
    }
}

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

    @Composable
    fun getTypography(size: ButtonSize): TextStyle =
        when (this) {
            PRIMARY, SECONDARY -> size.typography
            ASSISTIVE -> {
                when (size) {
                    XLARGE, LARGE -> BakeRoadTheme.typography.bodyMediumMedium
                    MEDIUM -> BakeRoadTheme.typography.bodySmallMedium
                    SMALL, XSMALL -> BakeRoadTheme.typography.body2XsmallMedium
                }
            }
        }
}