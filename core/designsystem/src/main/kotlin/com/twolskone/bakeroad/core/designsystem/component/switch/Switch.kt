package com.twolskone.bakeroad.core.designsystem.component.switch

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import kotlin.math.roundToInt

private val TrackShape = RoundedCornerShape(100.dp)
private val ThumbShape = CircleShape

private const val SwitchAnimationDuration = 500

@Composable
fun BakeRoadSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    enabled: Boolean = true,
    size: SwitchSize,
    onCheckedChange: (Boolean) -> Unit
) {
    val density = LocalDensity.current
    val interactionSource = remember { MutableInteractionSource() }
    val trackColor = if (checked) BakeRoadTheme.colorScheme.Primary500 else BakeRoadTheme.colorScheme.Gray100
    val thumbsStartX = with(density) { size.padding.toPx() }
    val thumbEndX = with(density) { (size.trackSize.width - size.thumbSize.width - size.padding).toPx() }
    val thumbOffsetX by animateFloatAsState(
        targetValue = if (checked) thumbEndX else thumbsStartX,
        animationSpec = tween(durationMillis = SwitchAnimationDuration),
        label = "Switch"
    )

    Box(
        modifier = modifier
            .size(size.trackSize)
            .graphicsLayer { alpha = if (enabled) 1f else 0.43f }
            .background(color = trackColor, shape = TrackShape)
            .toggleable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Switch,
                value = checked,
                enabled = enabled,
                onValueChange = onCheckedChange
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = modifier
                .offset { IntOffset(x = thumbOffsetX.roundToInt(), y = 0) }
                .size(size.thumbSize)
                .indication(
                    interactionSource = interactionSource,
                    indication = ripple(
                        bounded = false,
                        radius = size.thumbSize.width / 2 + size.padding,
                    )
                )
                .background(color = BakeRoadTheme.colorScheme.White, shape = ThumbShape)
        )
    }
}

enum class SwitchSize {
    SMALL, MEDIUM;

    val trackSize: DpSize
        @Composable
        get() = when (this) {
            SMALL -> DpSize(width = 39.dp, height = 24.dp)
            MEDIUM -> DpSize(width = 52.dp, height = 32.dp)
        }

    val thumbSize: DpSize
        @Composable
        get() = when (this) {
            SMALL -> DpSize(width = 18.dp, height = 18.dp)
            MEDIUM -> DpSize(width = 24.dp, height = 24.dp)
        }

    val padding: Dp
        @Composable
        get() = when (this) {
            SMALL -> 3.dp
            MEDIUM -> 4.dp
        }
}

@Preview
@Composable
private fun BakeRoadSwitchPreview() {
    BakeRoadTheme {
        var checked by remember { mutableStateOf(false) }

        Box(modifier = Modifier.background(color = BakeRoadTheme.colorScheme.White)) {
            BakeRoadSwitch(
                checked = checked,
                enabled = true,
                size = SwitchSize.MEDIUM,
                onCheckedChange = { checked = it }
            )
        }
    }
}