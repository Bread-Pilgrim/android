package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.extension.noRippleSingleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun ReviewMultiToggleButton(
    modifier: Modifier = Modifier,
    selectedOptionIndex: Int,
    optionList: ImmutableList<String>,
    colors: ReviewMultiToggleButtonColors = ReviewMultiToggleButtonDefaults.buttonColors(),
    shape: Shape = ReviewMultiToggleButtonDefaults.containerShape,
    optionShape: Shape = ReviewMultiToggleButtonDefaults.optionContainerShape,
    unselectedTextStyle: TextStyle = ReviewMultiToggleButtonDefaults.unselectedTextStyle,
    selectedTextStyle: TextStyle = ReviewMultiToggleButtonDefaults.selectedTextStyle,
    state: MultiToggleState = rememberMultiToggleState(selectedIndex = selectedOptionIndex),
    onSelect: (Int) -> Unit
) {
    if (optionList.size <= 1) return
    if ((selectedOptionIndex in 0 until optionList.size).not()) return

    LaunchedEffect(selectedOptionIndex) {
        state.selectOption(scope = this, index = selectedOptionIndex)
    }

    Layout(
        modifier = modifier
            .clip(shape)
            .background(color = colors.containerColor),
        content = {
            optionList.forEachIndexed { index, option ->
                Box(
                    modifier = Modifier
                        .layoutId(MultiToggleButtonLayoutType.Option)
                        .noRippleSingleClickable { onSelect(index) },
                    contentAlignment = Alignment.Center
                ) {
                    val textStyle = if (index == selectedOptionIndex) {
                        selectedTextStyle.copy(color = colors.selectedContentColor)
                    } else {
                        unselectedTextStyle.copy(color = colors.unselectedContentColor)
                    }

                    ProvideTextStyle(value = textStyle) {
                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            text = option,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .layoutId(MultiToggleButtonLayoutType.Background)
                        .padding(4.dp)
                        .clip(optionShape)
                        .background(color = colors.optionContainerColor, shape = optionShape)
                )
            }
        }
    ) { measurables, constraints ->
        val optionWidth = constraints.maxWidth / optionList.size
        val optionConstraints = Constraints.fixed(
            width = optionWidth,
            height = constraints.maxHeight
        )
        val optionPlaceables = measurables
            .filter { measurable -> measurable.layoutId == MultiToggleButtonLayoutType.Option }
            .map { measurable -> measurable.measure(optionConstraints) }
        val backgroundPlaceable = measurables
            .first { measurable -> measurable.layoutId == MultiToggleButtonLayoutType.Background }
            .measure(optionConstraints)

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            backgroundPlaceable.placeRelative(
                x = (optionWidth * state.selectedIndex).toInt(),
                y = 0
            )
            optionPlaceables.forEachIndexed { index, placeable ->
                placeable.placeRelative(
                    x = optionWidth * index,
                    y = 0
                )
            }
        }
    }
}

@Immutable
internal data class ReviewMultiToggleButtonColors(
    val containerColor: Color,
    val optionContainerColor: Color,
    val unselectedContentColor: Color,
    val selectedContentColor: Color
)

internal object ReviewMultiToggleButtonDefaults {
    val unselectedTextStyle: TextStyle
        @Composable
        get() = BakeRoadTheme.typography.bodyXsmallMedium

    val selectedTextStyle: TextStyle
        @Composable
        get() = BakeRoadTheme.typography.bodyXsmallSemibold

    val containerShape: Shape
        @Composable
        get() = CircleShape

    val optionContainerShape: Shape
        @Composable
        get() = CircleShape

    @Composable
    fun buttonColors(
        containerColor: Color = BakeRoadTheme.colorScheme.Gray40,
        contentColor: Color = BakeRoadTheme.colorScheme.Gray200,
        optionContainerColor: Color = BakeRoadTheme.colorScheme.Secondary100,
        optionContentColor: Color = BakeRoadTheme.colorScheme.Secondary500
    ) = ReviewMultiToggleButtonColors(
        containerColor = containerColor,
        unselectedContentColor = contentColor,
        optionContainerColor = optionContainerColor,
        selectedContentColor = optionContentColor
    )
}

private enum class MultiToggleButtonLayoutType {
    Option, Background
}

@Stable
internal interface MultiToggleState {
    val selectedIndex: Float
    fun selectOption(scope: CoroutineScope, index: Int)
}

@Stable
private class MultiToggleStateImpl(
    selectedIndex: Int
) : MultiToggleState {

    private var _selectedIndex = Animatable(initialValue = selectedIndex.toFloat())
    override val selectedIndex: Float
        get() = _selectedIndex.value

    private val animationSpec = tween<Float>(
        durationMillis = ANIMATION_DURATION,
        easing = FastOutSlowInEasing
    )

    override fun selectOption(scope: CoroutineScope, index: Int) {
        scope.launch {
            _selectedIndex.animateTo(
                targetValue = index.toFloat(),
                animationSpec = animationSpec
            )
        }
    }

    private companion object {
        private const val ANIMATION_DURATION = 300
    }
}

@Composable
internal fun rememberMultiToggleState(selectedIndex: Int): MultiToggleState = remember {
    MultiToggleStateImpl(selectedIndex)
}

@Preview
@Composable
private fun JugoBatJangMultiToggleButtonPreview() {
    BakeRoadTheme {
        var selectedIndex by remember { mutableIntStateOf(0) }

        ReviewMultiToggleButton(
            modifier = Modifier
                .height(48.dp),
            optionList = persistentListOf("방문자 리뷰 (3개)", "내가 쓴 리뷰 (2개)"),
            selectedOptionIndex = selectedIndex,
            onSelect = { selectedIndex = it }
        )
    }
}