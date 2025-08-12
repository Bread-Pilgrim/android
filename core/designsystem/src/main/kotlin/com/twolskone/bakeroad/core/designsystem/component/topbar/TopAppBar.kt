package com.twolskone.bakeroad.core.designsystem.component.topbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirst
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

/**
 * BakeRoad TopAppBar
 * * Height : 56dp
 * * HorizontalPadding : 12dp
 *
 * Actions 내 컴포넌트 자체 패딩을 주어 최종 디자인 가이드에 맞게 재배치 필요.
 * (디자인 가이드는 IconPadding 에 대한 고려가 되어있지 않기 때문)
 *
 * @param leftActions   좌측 영역
 * @param title         가운데 영역
 * @param rightActions  우측 영역
 */
@Composable
fun BakeRoadTopAppBar(
    modifier: Modifier = Modifier,
    containerColor: Color = BakeRoadTheme.colorScheme.White,
    contentColor: Color = BakeRoadTheme.colorScheme.Black,
    iconContentColor: Color = BakeRoadTheme.colorScheme.Black,
    title: @Composable () -> Unit = {},
    leftActions: @Composable () -> Unit = {},
    rightActions: @Composable () -> Unit = {},
) {
    BakeRoadTopAppBarLayout(
        modifier = modifier,
        maxHeight = TopAppBarMaxHeight,
        color = containerColor,
        titleTextTextStyle = BakeRoadTheme.typography.headingSmallBold.copy(color = contentColor),
        leftIconContentColor = iconContentColor,
        rightIconContentColor = iconContentColor,
        title = title,
        leftActions = leftActions,
        rightActions = rightActions
    )
}

internal val TopAppBarMaxHeight = 56.dp
private val TopAppBarHorizontalPadding = 12.dp

@Composable
internal fun BakeRoadTopAppBarLayout(
    modifier: Modifier = Modifier,
    maxHeight: Dp,
    color: Color,
    leftIconContentColor: Color,
    titleTextTextStyle: TextStyle,
    rightIconContentColor: Color,
    title: @Composable () -> Unit,
    leftActions: @Composable () -> Unit,
    rightActions: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier
            .statusBarsPadding()
            .background(color = color)
            // clip after padding so we don't show the title over the inset area
            .clipToBounds()
            .heightIn(max = maxHeight),
        content = {
            // Left actions.
            Box(
                modifier = Modifier
                    .layoutId("leftActions")
                    .padding(
                        start = TopAppBarHorizontalPadding
                    )
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides leftIconContentColor,
                    content = leftActions
                )
            }
            // Title.
            Box(
                modifier = Modifier
                    .layoutId("title")
                    .padding(horizontal = TopAppBarHorizontalPadding)
            ) {
                CompositionLocalProvider(
                    LocalTextStyle provides titleTextTextStyle,
                    content = title
                )
            }
            // Right actions.
            Box(
                modifier = Modifier
                    .layoutId("rightActions")
                    .padding(end = TopAppBarHorizontalPadding)
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides rightIconContentColor,
                    content = rightActions
                )
            }
        }

    ) { measurables, constraints ->
        val leftIconPlaceable = measurables
            .fastFirst { it.layoutId == "leftActions" }
            .measure(constraints.copy(minWidth = 0))
        val rightIconsPlaceable = measurables
            .fastFirst { it.layoutId == "rightActions" }
            .measure(constraints.copy(minWidth = 0))
        val maxTitleWidth = if (constraints.maxWidth == Constraints.Infinity) {
            constraints.maxWidth
        } else {
            (constraints.maxWidth - leftIconPlaceable.width - rightIconsPlaceable.width).coerceAtLeast(0)
        }
        val titlePlaceable = measurables
            .fastFirst { it.layoutId == "title" }
            .measure(constraints.copy(minWidth = 0, maxWidth = maxTitleWidth))

        layout(constraints.maxWidth, constraints.maxHeight) {
            // Left actions.
            leftIconPlaceable.placeRelative(
                x = 0,
                y = (constraints.maxHeight - leftIconPlaceable.height) / 2
            )
            // Title.
            titlePlaceable.placeRelative(
                x = run {
                    var baseX = (constraints.maxWidth - titlePlaceable.width) / 2
                    if (baseX < leftIconPlaceable.width) {
                        // May happen if the navigation is wider than the actions and the
                        // title is long. In this case, prioritize showing more of the title
                        // by
                        // offsetting it to the right.
                        baseX += (leftIconPlaceable.width - baseX)
                    } else if (
                        baseX + titlePlaceable.width >
                        constraints.maxWidth - rightIconsPlaceable.width
                    ) {
                        // May happen if the actions are wider than the navigation and the
                        // title
                        // is long. In this case, offset to the left.
                        baseX +=
                            ((constraints.maxWidth - rightIconsPlaceable.width) -
                                    (baseX + titlePlaceable.width))
                    }
                    baseX
                },
                y = (constraints.maxHeight - titlePlaceable.height) / 2
            )
            // Right actions.
            rightIconsPlaceable.placeRelative(
                x = constraints.maxWidth - rightIconsPlaceable.width,
                y = (constraints.maxHeight - rightIconsPlaceable.height) / 2
            )
        }
    }
}

@Composable
fun BakeRoadTopAppBarIcon(
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    @DrawableRes iconRes: Int,
    contentDescription: String? = null,
    backgroundColor: Color = BakeRoadTheme.colorScheme.White,
    tint: Color = BakeRoadTheme.colorScheme.Black,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .singleClickable { onClick() }
            .padding(4.dp)
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            imageVector = ImageVector.vectorResource(id = iconRes),
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Preview
@Composable
private fun BakeRoadTopAppBarLayoutPreview() {
    BakeRoadTheme {
        BakeRoadTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            leftActions = {
                Box(
                    modifier = Modifier
                        .singleClickable { }
                        .padding(4.dp),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            title = {
                Text(text = "내 취향 빵집")
            },
            rightActions = {
                Row {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
    }
}
