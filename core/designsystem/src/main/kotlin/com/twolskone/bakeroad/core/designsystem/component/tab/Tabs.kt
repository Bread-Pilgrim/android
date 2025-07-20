package com.twolskone.bakeroad.core.designsystem.component.tab

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFold
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import com.twolskone.bakeroad.core.designsystem.component.tab.BakeRoadTabRowDefaults.tabIndicatorOffset
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100

private val HorizontalTabTextPadding = 6.dp
private val VerticalTabTextPadding = 10.dp
private val TabSpacing = 6.dp

private val TabRowIndicatorSpec: AnimationSpec<Dp> =
    tween(durationMillis = 250, easing = FastOutSlowInEasing)

private val ScrollableTabRowScrollSpec: AnimationSpec<Float> =
    tween(durationMillis = 250, easing = FastOutSlowInEasing)

@Composable
fun BakeRoadTab(
    modifier: Modifier = Modifier,
    selected: Boolean,
    selectedContentColor: Color = BakeRoadTheme.colorScheme.Gray990,
    unselectedContentColor: Color = BakeRoadTheme.colorScheme.Gray200,
    textStyle: TextStyle = BakeRoadTheme.typography.bodyMediumSemibold,
    onClick: () -> Unit,
    text: @Composable () -> Unit
) {
    val transition = updateTransition(targetState = selected)
    val color by transition.animateColor(
        transitionSpec = {
            if (false isTransitioningTo true) {
                tween(
                    durationMillis = TabFadeInAnimationDuration,
                    delayMillis = TabFadeInAnimationDelay,
                    easing = LinearEasing
                )
            } else {
                tween(durationMillis = TabFadeOutAnimationDuration, easing = LinearEasing)
            }
        },
        targetValueByState = {
            if (it) selectedContentColor else unselectedContentColor
        }
    )
    CompositionLocalProvider(LocalContentColor provides color) {
        Box(
            modifier = modifier
                .selectable(
                    selected = selected,
                    onClick = onClick,
                    role = Role.Tab
                )
                .padding(horizontal = HorizontalTabTextPadding, vertical = VerticalTabTextPadding)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            ProvideTextStyle(value = textStyle, content = text)
        }
    }
}

@Composable
fun BakeRoadScrollableTabRow(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    containerColor: Color = BakeRoadTheme.colorScheme.White,
    contentColor: Color = BakeRoadTheme.colorScheme.Gray990,
    edgePadding: Dp = 16.dp,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
        BakeRoadTabRowDefaults.Indicator(modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]))
    },
    divider: @Composable () -> Unit = @Composable {
        HorizontalDivider(
            thickness = 1.dp,
            color = BakeRoadTheme.colorScheme.Gray50
        )
    },
    tabs: @Composable () -> Unit
) {
    BakeRoadScrollableTabRowWithSubcomposeImpl(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        indicator = indicator,
        containerColor = containerColor,
        contentColor = contentColor,
        edgePadding = edgePadding,
        divider = divider,
        tabs = tabs,
        scrollState = rememberScrollState()
    )
}

object BakeRoadTabRowDefaults {

    @Composable
    fun Indicator(
        modifier: Modifier = Modifier,
        height: Dp = 1.dp,
        color: Color = BakeRoadTheme.colorScheme.Gray990
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(height)
                .background(color = color)
        )
    }

    /**
     * [Modifier] that takes up all the available width inside the [TabRow], and then animates the
     * offset of the indicator it is applied to, depending on the [currentTabPosition].
     *
     * @param currentTabPosition [TabPosition] of the currently selected tab. This is used to
     *   calculate the offset of the indicator this modifier is applied to, as well as its width.
     */
    fun Modifier.tabIndicatorOffset(currentTabPosition: TabPosition): Modifier =
        composed(
            inspectorInfo =
                debugInspectorInfo {
                    name = "tabIndicatorOffset"
                    value = currentTabPosition
                }
        ) {
            val currentTabWidth by animateDpAsState(
                targetValue = currentTabPosition.width,
                animationSpec = TabRowIndicatorSpec
            )
            val indicatorOffset by animateDpAsState(
                targetValue = currentTabPosition.left,
                animationSpec = TabRowIndicatorSpec
            )
            fillMaxWidth()
                .wrapContentSize(Alignment.BottomStart)
                .offset { IntOffset(x = indicatorOffset.roundToPx(), y = 0) }
                .width(currentTabWidth)
        }
}

@Composable
private fun BakeRoadScrollableTabRowWithSubcomposeImpl(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit,
    containerColor: Color,
    contentColor: Color,
    edgePadding: Dp,
    divider: @Composable () -> Unit,
    tabs: @Composable () -> Unit,
    scrollState: ScrollState
) {
    val screenWidth = LocalWindowInfo.current.containerSize.width

    Surface(
        modifier = modifier,
        color = containerColor,
        contentColor = contentColor
    ) {
        val coroutineScope = rememberCoroutineScope()
        val scrollableTabData =
            remember(scrollState, coroutineScope) {
                ScrollableTabData(scrollState = scrollState, coroutineScope = coroutineScope)
            }
        SubcomposeLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.CenterStart)
                .horizontalScroll(scrollState)
                .selectableGroup()
                .clipToBounds()
        ) { constraints ->
//            val minTabWidth = ScrollableTabRowMinimumTabWidth.roundToPx()
            val padding = edgePadding.roundToPx()

            val tabMeasurables = subcompose(TabSlots.Tabs, tabs)

            val layoutHeight =
                tabMeasurables.fastFold(initial = 0) { curr, measurable ->
                    maxOf(curr, measurable.maxIntrinsicHeight(Constraints.Infinity))
                }

            val tabConstraints =
                constraints.copy(
//                    minWidth = minTabWidth,
                    minHeight = layoutHeight,
                    maxHeight = layoutHeight,
                )

            val tabPlaceables = mutableListOf<Placeable>()
            val tabContentWidths = mutableListOf<Dp>()
            tabMeasurables.fastForEach {
                val placeable = it.measure(tabConstraints)
                val contentWidth =
                    minOf(it.maxIntrinsicWidth(placeable.height), placeable.width).toDp()
//                contentWidth -= HorizontalTextPadding * 2
                tabPlaceables.add(placeable)
                tabContentWidths.add(contentWidth)
            }

            val layoutWidth =
                tabPlaceables.fastFold(initial = padding * 2) { curr, measurable ->
                    curr + measurable.width
                }

            // Position the children.
            layout(screenWidth, layoutHeight) {
                // Place the tabs
                val tabPositions = mutableListOf<TabPosition>()
                var left = padding
                tabPlaceables.fastForEachIndexed { index, placeable ->
                    placeable.placeRelative(left, 0)
                    tabPositions.add(
                        TabPosition(
                            left = left.toDp(),
                            width = placeable.width.toDp(),
                            contentWidth = tabContentWidths[index]
                        )
                    )
                    left += placeable.width + TabSpacing.roundToPx()
                }

                // The divider is measured with its own height, and width equal to the total width
                // of the tab row, and then placed on top of the tabs.
                subcompose(TabSlots.Divider, divider).fastForEach {
                    val placeable =
                        it.measure(
                            constraints.copy(
                                minHeight = 0,
                                minWidth = layoutWidth,
                                maxWidth = screenWidth
                            )
                        )
                    placeable.placeRelative(0, layoutHeight - placeable.height)
                }

                // The indicator container is measured to fill the entire space occupied by the tab
                // row, and then placed on top of the divider.
                subcompose(TabSlots.Indicator) { indicator(tabPositions) }
                    .fastForEach {
                        it.measure(Constraints.fixed(layoutWidth, layoutHeight)).placeRelative(0, 0)
                    }

                scrollableTabData.onLaidOut(
                    density = this@SubcomposeLayout,
                    edgeOffset = padding,
                    tabPositions = tabPositions,
                    selectedTab = selectedTabIndex
                )
            }
        }
    }
}

private enum class TabSlots {
    Tabs, Divider, Indicator
}

@Immutable
class TabPosition internal constructor(val left: Dp, val width: Dp, val contentWidth: Dp) {

    val right: Dp
        get() = left + width

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TabPosition) return false

        if (left != other.left) return false
        if (width != other.width) return false
        if (contentWidth != other.contentWidth) return false

        return true
    }

    override fun hashCode(): Int {
        var result = left.hashCode()
        result = 31 * result + width.hashCode()
        result = 31 * result + contentWidth.hashCode()
        return result
    }

    override fun toString(): String {
        return "TabPosition(left=$left, right=$right, width=$width, contentWidth=$contentWidth)"
    }
}

private class ScrollableTabData(
    private val scrollState: ScrollState,
    private val coroutineScope: CoroutineScope
) {
    private var selectedTab: Int? = null

    fun onLaidOut(
        density: Density,
        edgeOffset: Int,
        tabPositions: List<TabPosition>,
        selectedTab: Int
    ) {
        // Animate if the new tab is different from the old tab, or this is called for the first
        // time (i.e selectedTab is `null`).
        if (this.selectedTab != selectedTab) {
            this.selectedTab = selectedTab
            tabPositions.getOrNull(selectedTab)?.let {
                // Scrolls to the tab with [tabPosition], trying to place it in the center of the
                // screen or as close to the center as possible.
                val calculatedOffset = it.calculateTabOffset(density, edgeOffset, tabPositions)
                if (scrollState.value != calculatedOffset) {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(
                            calculatedOffset,
                            animationSpec = ScrollableTabRowScrollSpec
                        )
                    }
                }
            }
        }
    }

    /**
     * @return the offset required to horizontally center the tab inside this TabRow. If the tab is
     *   at the start / end, and there is not enough space to fully centre the tab, this will just
     *   clamp to the min / max position given the max width.
     */
    private fun TabPosition.calculateTabOffset(
        density: Density,
        edgeOffset: Int,
        tabPositions: List<TabPosition>
    ): Int =
        with(density) {
            val totalTabRowWidth = tabPositions.last().right.roundToPx() + edgeOffset
            val visibleWidth = totalTabRowWidth - scrollState.maxValue
            val tabOffset = left.roundToPx()
            val scrollerCenter = visibleWidth / 2
            val tabWidth = width.roundToPx()
            val centeredTabOffset = tabOffset - (scrollerCenter - tabWidth / 2)
            // How much space we have to scroll. If the visible width is <= to the total width, then
            // we have no space to scroll as everything is always visible.
            val availableSpace = (totalTabRowWidth - visibleWidth).coerceAtLeast(0)
            return centeredTabOffset.coerceIn(0, availableSpace)
        }
}