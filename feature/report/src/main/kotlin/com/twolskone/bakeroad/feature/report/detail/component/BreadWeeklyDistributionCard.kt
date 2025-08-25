package com.twolskone.bakeroad.feature.report.detail.component

import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.type.DayOfWeek
import com.twolskone.bakeroad.core.ui.extension.toLabel
import com.twolskone.bakeroad.feature.report.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

/**
 * 요일별 빵 섭취 패턴 카드
 * @param data  요일별 빵 섭취 개수
 */
@Composable
internal fun BreadWeeklyDistributionCard(
    modifier: Modifier = Modifier,
    data: ImmutableList<Pair<DayOfWeek, Int>>
) {
    val maxValue = data.maxOf { it.second }

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
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            if (maxValue == 0) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .align(Alignment.TopCenter),
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(17.83.dp)
            ) {
                data.fastForEach { (dayOfWeek, value) ->
                    WeeklyBreadConsumptionBar(
                        modifier = Modifier.weight(1f),
                        isTopRank = value > 0 && maxValue == value,
                        isEmpty = value == 0,
                        dayOfWeek = dayOfWeek,
                        heightRadio = (value.toFloat() / maxValue).coerceAtLeast(0f)
                    )
                }
            }
        }
    }
}

private val BarMaxHeight = 80.dp
private val BarMinHeight = 4.dp
private val BarShape = RoundedCornerShape(8.dp)

@Composable
private fun WeeklyBreadConsumptionBar(
    modifier: Modifier = Modifier,
    isTopRank: Boolean,
    isEmpty: Boolean,
    dayOfWeek: DayOfWeek,
    @FloatRange(from = 0.0, to = 1.0) heightRadio: Float,
) {
    val defaultColor = BakeRoadTheme.colorScheme.Primary200
    val emptyColor = BakeRoadTheme.colorScheme.Gray50
    val topColors = listOf(
        BakeRoadTheme.colorScheme.Primary600,
        BakeRoadTheme.colorScheme.Primary500,
        BakeRoadTheme.colorScheme.Primary400,
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(width = 34.dp, height = 30.dp)) {
            if (isTopRank) {
                RankBalloon(
                    modifier = Modifier.fillMaxSize(),
                    rank = 1
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .height(BarMaxHeight)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((BarMaxHeight - BarMinHeight) * heightRadio + BarMinHeight)
                    .then(
                        if (isTopRank) {
                            Modifier.background(
                                brush = Brush.verticalGradient(colors = topColors),
                                shape = BarShape
                            )
                        } else {
                            Modifier.background(
                                color = if (isEmpty) emptyColor else defaultColor,
                                shape = BarShape
                            )
                        }
                    )
                    .align(Alignment.BottomCenter)
            )
        }
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = dayOfWeek.toLabel,
            style = BakeRoadTheme.typography.body2XsmallSemibold,
        )
    }
}

@Composable
private fun RankBalloon(
    modifier: Modifier = Modifier,
    rank: Int,
    tailHeight: Dp = 5.dp,
    containerColor: Color = BakeRoadTheme.colorScheme.Black,
    contentColor: Color = BakeRoadTheme.colorScheme.White,
    labelTextStyle: TextStyle = BakeRoadTheme.typography.body2XsmallMedium
) {
    Box(modifier = modifier) {
        Canvas(modifier = modifier) {
            val width = size.width
            val height = size.height
            val cornerRadius = width * 0.3f
            val tailWidth = width * 0.25f
            val rectHeight = height - tailHeight.toPx()
            val path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(0f, 0f, width, rectHeight),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )
                )
                moveTo(width / 2f - tailWidth / 2f, rectHeight)
                lineTo(width / 2f, height)
                lineTo(width / 2f + tailWidth / 2f, rectHeight)
                close()
            }

            drawPath(path = path, color = containerColor)
        }

        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = tailHeight),
            text = stringResource(id = R.string.feature_report_format_weekly_rank, rank),
            color = contentColor,
            style = labelTextStyle,
        )
    }
}

@Preview
@Composable
private fun BreadWeeklyDistributionCardPreview() {
    BakeRoadTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BakeRoadTheme.colorScheme.White)
        ) {
            BreadWeeklyDistributionCard(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                data = DayOfWeek.entries.map {
                    it to (0..0).random()
                }.toImmutableList()
            )
        }
    }
}