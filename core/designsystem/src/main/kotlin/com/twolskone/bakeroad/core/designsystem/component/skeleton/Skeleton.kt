package com.twolskone.bakeroad.core.designsystem.component.skeleton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.extension.shimmerEffect

/**
 * LineChips row skeleton
 */
@Composable
fun LineChipsSkeleton(
    modifier: Modifier = Modifier,
    widths: Array<Dp> = arrayOf(73.dp, 73.dp, 73.dp, 73.dp, 73.dp)
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        widths.forEach { width ->
            Box(
                modifier = Modifier
                    .size(width = width, height = 32.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
        }
    }
}

/**
 * Chips row skeleton
 */
@Composable
fun ChipsSkeleton(
    modifier: Modifier = Modifier,
    widths: Array<Dp> = arrayOf(45.dp, 45.dp, 57.dp, 45.dp, 69.dp)
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        widths.forEach { width ->
            Box(
                modifier = Modifier
                    .size(width = width, height = 32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
        }
    }
}

/**
 * Title skeleton.
 * @param titleWidth    제목 너비
 * @param skipButton    우측 버튼 숨김 여부
 */
@Composable
fun TitleSkeleton(
    modifier: Modifier = Modifier,
    titleWidth: Dp,
    skipButton: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(width = titleWidth, height = 28.dp)
                .clip(CircleShape)
                .shimmerEffect()
        )
        if (!skipButton) {
            Box(
                modifier = Modifier
                    .size(width = 43.dp, height = 12.25.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
        }
    }
}

/**
 * Recommend bakeries skeleton
 */
@Composable
fun SimpleBakeriesSkeleton(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(5) {
            Column {
                Box(
                    modifier = Modifier
                        .size(116.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .size(width = 97.dp, height = 12.25.dp)
                        .clip(CircleShape)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .size(width = 76.dp, height = 12.25.dp)
                        .clip(CircleShape)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .size(width = 61.dp, height = 23.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .shimmerEffect()
                )
            }
        }
    }
}

/**
 * Bakeries skeleton.
 */
@Composable
fun BakeriesSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        repeat(5) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .width(148.dp)
                        .aspectRatio(5f / 4f)
                        .clip(RoundedCornerShape(12.dp))
                        .shimmerEffect()
                )
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Box(
                        modifier = Modifier
                            .size(width = 58.dp, height = 23.dp)
                            .clip(CircleShape)
                            .shimmerEffect()
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(width = 97.dp, height = 12.dp)
                            .clip(CircleShape)
                            .shimmerEffect()

                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(width = 76.dp, height = 12.dp)
                            .clip(CircleShape)
                            .shimmerEffect()
                    )
                    FlowRow(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        maxLines = 2,
                        maxItemsInEachRow = 2,
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .size(width = 58.dp, height = 23.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .shimmerEffect()
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * TourAreas skeleton
 */
@Composable
fun TourAreasSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        repeat(5) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(12.dp))
                    .shimmerEffect()
            )
            Row(modifier = Modifier.padding(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(width = 37.dp, height = 23.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .padding(top = 6.dp)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .size(width = 87.dp, height = 23.dp)
                        .clip(CircleShape)
                        .padding(start = 4.dp)
                        .shimmerEffect()
                )
            }
            Box(
                modifier = Modifier
                    .size(width = 104.dp, height = 18.dp)
                    .clip(CircleShape)
                    .padding(top = 4.dp)
                    .shimmerEffect()
            )
        }
    }
}

/**
 * Reviews skeletons
 */
@Composable
fun ReviewsSkeleton(
    modifier: Modifier = Modifier,
    heights: Array<Dp> = arrayOf(171.dp, 307.dp, 307.dp, 40.dp)
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        heights.forEach { height ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = height)
                    .clip(RoundedCornerShape(12.dp))
                    .shimmerEffect()
            )
        }
    }
}