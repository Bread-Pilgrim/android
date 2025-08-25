package com.twolskone.bakeroad.feature.report.detail.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.report.R
import kotlin.math.cos
import kotlin.math.sin
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

private val ChartSize = 90.dp
private val BreadTypeColors: ImmutableList<Color>
    @Composable
    get() = persistentListOf(
        BakeRoadTheme.colorScheme.Primary500,
        BakeRoadTheme.colorScheme.Secondary500,
        BakeRoadTheme.colorScheme.Success500
    )

private const val BreadTypeMaxCount = 3

/**
 * 가장 많이 먹은 빵 종류 순위카드
 * @param breadTypeList 빵 종류 목록 (빵이름:횟수) // 최대 3가지
 */
@Composable
internal fun BreadTypeRankCard(
    modifier: Modifier = Modifier,
    breadTypeList: ImmutableList<Pair<String, Int>>
) {
    val list = breadTypeList.take(BreadTypeMaxCount)

    Card(
        modifier = modifier,
        shape = CardShape,
        colors = CardColors(
            containerColor = BakeRoadTheme.colorScheme.White,
            contentColor = BakeRoadTheme.colorScheme.Gray800,
            disabledContainerColor = BakeRoadTheme.colorScheme.Gray100,
            disabledContentColor = BakeRoadTheme.colorScheme.Gray500
        ),
        elevation = CardDefaults.cardElevation(CardElevation)
    ) {
        when (list.size) {
            0 -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UnderlineText(
                        text = stringResource(id = R.string.feature_report_empty_bread_type_1),
                        textStyle = BakeRoadTheme.typography.bodyXsmallMedium
                    )
                    Text(
                        text = stringResource(id = R.string.feature_report_empty_bread_type_2),
                        style = BakeRoadTheme.typography.bodyXsmallMedium
                    )
                }
            }

            1 -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UnderlineText(
                        text = stringResource(id = R.string.feature_report_only_one_bread_type_1, list.first().first),
                        textStyle = BakeRoadTheme.typography.bodyXsmallMedium
                    )
                    Text(
                        text = stringResource(id = R.string.feature_report_empty_bread_type_2),
                        style = BakeRoadTheme.typography.bodyXsmallMedium
                    )
                }
            }

            else -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(vertical = 16.dp, horizontal = 18.5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    BreadTypeRankChart(
                        modifier = Modifier.size(ChartSize),
                        data = list.toPercentages(),
                        colors = BreadTypeColors
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        list.fastForEachIndexed { i, (breadName, count) ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(color = BreadTypeColors[i], shape = CircleShape)
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = breadName,
                                    style = BakeRoadTheme.typography.bodyXsmallMedium
                                )
                                BakeRoadChip(
                                    modifier = Modifier.widthIn(min = 34.dp),
                                    selected = true,
                                    color = ChipColor.GRAY,
                                    size = ChipSize.SMALL,
                                    label = { Text(text = stringResource(id = R.string.feature_report_format_visited_count, count)) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BreadTypeRankChart(
    modifier: Modifier = Modifier,
    data: ImmutableList<Float>,
    colors: ImmutableList<Color>,
    gap: Dp = 5.dp,
    startAngle: Float = -90f
) {
    val total = data
        .sum()
        .coerceAtLeast(1f)

    Canvas(
        modifier.graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    ) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val r = minOf(size.width, size.height) / 2f
        val rectTopLeft = Offset(cx - r, cy - r)
        val rectSize = Size(r * 2, r * 2)

        var cur = startAngle
        data.forEachIndexed { i, v ->
            val sweep = -360f * (v / total) // 반시계
            if (sweep != 0f) {
                drawArc(
                    color = colors[i],
                    startAngle = cur,
                    sweepAngle = sweep,
                    useCenter = true,
                    topLeft = rectTopLeft,
                    size = rectSize
                )
            }
            cur += sweep
        }

        val gapPx = gap.toPx()
        val boundaries = buildList {
            var a = startAngle
            data.forEach { v ->
                a += -360f * (v / total)    // 반시계
                add(a)
            }
        }

        boundaries.forEach { deg ->
            val rad = Math.toRadians(deg.toDouble())
            val end = Offset(
                x = cx + cos(rad).toFloat() * r,
                y = cy + sin(rad).toFloat() * r
            )
            drawLine(
                color = Color.Transparent,
                start = Offset(cx, cy),
                end = end,
                strokeWidth = gapPx,
                cap = StrokeCap.Round,
                blendMode = BlendMode.Clear
            )
        }
    }
}

// 가장 많이 먹은 빵 종류의 횟수 기준 비율을 계산
private fun List<Pair<String, Int>>.toPercentages(): ImmutableList<Float> {
    val maxCount = maxOf { it.second }
    return map { it.second.toFloat() / maxCount }.toImmutableList()
}

@Preview
@Composable
private fun BreadTypeRankCardPreview() {
    BakeRoadTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BakeRoadTheme.colorScheme.White)
        ) {
            BreadTypeRankCard(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                breadTypeList = persistentListOf(
                    "건강한 빵" to 3,
                    "달콤한 디저트 빵" to 8,
                    "케이크, 브라우니, 파이류" to 1
                )
            )
        }
    }
}