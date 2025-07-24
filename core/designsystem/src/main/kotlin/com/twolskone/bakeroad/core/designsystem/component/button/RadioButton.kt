package com.twolskone.bakeroad.core.designsystem.component.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import kotlinx.collections.immutable.ImmutableList

/**
 * RadioButton group.
 */
@Composable
fun BakeRoadRadioButtonGroup(
    modifier: Modifier = Modifier,
    radioButtonSize: RadioButtonSize,
    selectedIndex: Int,
    optionList: ImmutableList<String>,
    verticalPadding: Dp = 0.dp,
    onOptionSelect: (index: Int) -> Unit,
    text: @Composable RowScope.(index: Int) -> Unit
) {
    Column(modifier = modifier.selectableGroup()) {
        optionList.fastForEachIndexed { index, option ->
            Row(
                modifier = Modifier
                    .selectable(
                        selected = (index == selectedIndex),
                        onClick = { onOptionSelect(index) },
                        role = Role.RadioButton,
                        interactionSource = null,
                        indication = null
                    )
                    .padding(vertical = verticalPadding)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BakeRoadRadioButton(
                    size = radioButtonSize,
                    selected = (index == selectedIndex),
                    onClick = null
                )
                text(index)
            }
        }
    }
}

@Composable
private fun BakeRoadRadioButton(
    modifier: Modifier = Modifier,
    size: RadioButtonSize,
    selected: Boolean,
    enabled: Boolean = true,
    colors: BakeRoadRadioButtonColors = BakeRoadRadioButtonDefaults.colors(),
    onClick: (() -> Unit)?
) {
    val innerDotRadius by animateDpAsState(
        targetValue = if (selected) size.innerDotRadius else 0.dp,
        animationSpec = tween(durationMillis = RadioAnimationDuration),
        label = "InnerDotRadiusAnimation"
    )
    val outerDotRadius by animateDpAsState(
        targetValue = if (selected) size.outerDotRadius else 0.dp,
        animationSpec = tween(durationMillis = RadioAnimationDuration),
        label = "OuterDotRadiusAnimation"
    )
    val outerRadioColor by animateColorAsState(
        targetValue = if (selected) colors.outerRadioColor else colors.innerRadioColor,
        animationSpec = tween(durationMillis = RadioAnimationDuration),
        label = "OuterRadioColorAnimation"
    )
//    val strokeColor by animateColorAsState(
//        targetValue = if (selected) colors.innerRadioColor else colors.strokeColor,
//        animationSpec = tween(durationMillis = RadioAnimationDuration),
//        label = "StrokeColorAnimation"
//    )
    val selectableModifier = if (onClick != null) {
        Modifier.selectable(
            selected = selected,
            onClick = onClick,
            enabled = enabled,
            role = Role.RadioButton
        )
    } else {
        Modifier
    }

    Canvas(
        modifier = modifier
            .then(selectableModifier)
            .wrapContentSize(Alignment.Center)
            .padding(RadioButtonPadding)
            .requiredSize(size = size.iconSize)
            .graphicsLayer { alpha = if (enabled) 1f else 0.43f }
    ) {
        if (selected) {
            // Outer radio.
            drawCircle(
                color = outerRadioColor,
                radius = outerDotRadius.toPx(),
                style = Fill
            )
        } else {
            // Stroke.
            drawCircle(
                color = colors.strokeColor,
                radius = (size.iconSize / 2).toPx() - (RadioStrokeWidth.toPx() / 2),
                style = Stroke(RadioStrokeWidth.toPx())
            )
        }
        // Inner radio.
        drawCircle(
            color = colors.innerRadioColor,
            radius = innerDotRadius.toPx(),
            style = Fill
        )
    }
}

private val RadioButtonPadding = 2.dp
private val RadioStrokeWidth = 1.5.dp
private const val RadioAnimationDuration = 100

object BakeRoadRadioButtonDefaults {

    @Composable
    fun colors(
        strokeColor: Color = BakeRoadTheme.colorScheme.Gray200,
        outerRadioColor: Color = BakeRoadTheme.colorScheme.Primary500,
        innerRadioColor: Color = BakeRoadTheme.colorScheme.White,
    ) = BakeRoadRadioButtonColors(
        strokeColor = strokeColor,
        outerRadioColor = outerRadioColor,
        innerRadioColor = innerRadioColor
    )
}

@Immutable
data class BakeRoadRadioButtonColors(
    val strokeColor: Color,
    val outerRadioColor: Color,
    val innerRadioColor: Color
)

enum class RadioButtonSize {
    NORMAL, SMALL;

    val iconSize: Dp
        get() = when (this) {
            NORMAL -> 20.dp
            SMALL -> 16.dp
        }

    val outerDotRadius: Dp
        get() = when (this) {
            NORMAL -> 10.dp
            SMALL -> 8.dp
        }

    val innerDotRadius: Dp
        get() = when (this) {
            NORMAL -> 4.dp
            SMALL -> 3.5.dp
        }
}

@Preview
@Composable
private fun BakeRoadRadioButtonPreview() {
    BakeRoadTheme {
        var selected by remember { mutableStateOf(true) }

        Box(
            modifier = Modifier
                .selectableGroup()
                .background(color = BakeRoadTheme.colorScheme.White)
        ) {
            BakeRoadRadioButton(
                size = RadioButtonSize.NORMAL,
                selected = selected,
                enabled = true,
                onClick = { selected = !selected }
            )
        }
    }
}