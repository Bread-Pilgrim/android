package com.twolskone.bakeroad.feature.report.detail.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.common.kotlin.extension.orZero
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.report.R
import kotlin.math.abs
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

private val ChartGradient = persistentListOf(
    Color(0xFFFFEBDC),
    Color(0xFFFFFFFF)
)
private val ChartPadding = 66.dp
private const val PriceUnit = 10000f    // 금액 단위

/**
 * 빵 소비 금액 카드
 * @param currentMonth  이번달
 * @param priceList     최근 3달 소비 금액 목록
 */
@Composable
internal fun BreadSpendingPriceCard(
    modifier: Modifier = Modifier,
    currentMonth: Int,
    priceList: ImmutableList<Int>
) {
    val density = LocalDensity.current
    val maxPrice = priceList.maxOrNull().orZero()

    Card(
        modifier = modifier,
        shape = CardShape,
        colors = CardColors(
            containerColor = BakeRoadTheme.colorScheme.White,
            contentColor = BakeRoadTheme.colorScheme.Gray950,
            disabledContainerColor = BakeRoadTheme.colorScheme.Gray100,
            disabledContentColor = BakeRoadTheme.colorScheme.Gray500
        ),
        elevation = CardDefaults.cardElevation(CardElevation)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val totalPrice = (priceList.sum() / PriceUnit).toString()
            val rawTitle = stringResource(R.string.feature_report_title_bread_spending_total_amount, totalPrice)
            val annotatedTitle = buildAnnotatedString {
                append(rawTitle)
                val start = rawTitle.indexOf(totalPrice)
                if (start >= 0) {
                    addStyle(
                        style = BakeRoadTheme.typography.headingSmallBold.copy(color = BakeRoadTheme.colorScheme.Primary500).toSpanStyle(),
                        start = start,
                        end = start + totalPrice.length
                    )
                }
            }
            Text(
                text = annotatedTitle,
                style = BakeRoadTheme.typography.bodyMediumSemibold
            )
            val priceGap = (priceList.getOrNull(0).orZero() - priceList.getOrNull(1).orZero()) / PriceUnit
            val rawPriceGap = stringResource(R.string.feature_report_format_ten_thousand_won, abs(priceGap).toString())
            val rawDescription = if (priceGap >= 0) {
                stringResource(id = R.string.feature_report_description_bread_more_spending, rawPriceGap)
            } else {
                stringResource(id = R.string.feature_report_description_bread_less_spending, rawPriceGap)
            }
            val annotatedDescription = buildAnnotatedString {
                append(rawDescription)
                val start = rawDescription.indexOf(rawPriceGap)
                if (start >= 0) {
                    addStyle(
                        style = BakeRoadTheme.typography.bodyXsmallSemibold.toSpanStyle(),
                        start = start,
                        end = start + rawPriceGap.length
                    )
                }
            }
            UnderlineText(
                modifier = Modifier.padding(top = 6.dp),
                annotatedText = annotatedDescription,
                textStyle = BakeRoadTheme.typography.bodyXsmallMedium
            )

            BoxWithConstraints(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                val parentWidth = constraints.maxWidth
                val chartWidthDp = with(density) { parentWidth.toDp() } - ChartPadding * 2
                val chartLabelWidthDp = chartWidthDp / 2

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BreadSpendingPriceChart(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .width(chartWidthDp)
                            .aspectRatio(2f / 1f),
                        values = priceList
                            .reversed()
                            .map { price -> price / maxPrice.toFloat() }
                            .toImmutableList()
                    )
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        (2 downTo 0).forEach { diff ->
                            ChartLabel(
                                modifier = Modifier.width(chartLabelWidthDp),
                                month = currentMonth - diff,
                                price = (priceList.getOrNull(diff).orZero() / PriceUnit).toString(),
                                isCurrentMonth = diff == 0
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BreadSpendingPriceChart(
    modifier: Modifier = Modifier,
    values: ImmutableList<Float>,
    strokeColor: Color = BakeRoadTheme.colorScheme.Primary500,
    strokeWidth: Dp = 2.dp,
    areaGradient: ImmutableList<Color> = ChartGradient
) {
    val data = buildList(3) {
        addAll(values)
        repeat(3 - size) { add(0, 0f) }
    }

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stepX = w / (data.lastIndex.coerceAtLeast(1))

        fun pxY(v: Float): Float {
            val clamped = v.coerceIn(0f, 1f)
            return h - clamped * (h * 0.95f) // 상단 5% 여백
        }

        val points = List(data.size) { i -> Offset(i * stepX, pxY(data[i])) }
//        val linePath = Path().apply { catmullRom(points, tension) }
        val linePath = Path().apply { monotoneCubic(points) }
        val areaPath = Path().apply {
            addPath(linePath)
            lineTo(points.last().x, h)
            lineTo(points.first().x, h)
            close()
        }

        // 그래프 아래 영역
        drawPath(
            path = areaPath,
            brush = Brush.verticalGradient(colors = areaGradient),
            alpha = 1f
        )
        // 그래프 (stroke)
        drawPath(
            path = linePath,
            color = strokeColor,
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun ChartLabel(
    modifier: Modifier = Modifier,
    month: Int,
    price: String,
    isCurrentMonth: Boolean
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.feature_report_format_month, month),
            style = BakeRoadTheme.typography.body3XsmallMedium.copy(
                color = if (isCurrentMonth) {
                    BakeRoadTheme.colorScheme.Primary500
                } else {
                    BakeRoadTheme.colorScheme.Gray800
                }
            )
        )
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = stringResource(id = R.string.feature_report_format_ten_thousand_won, price),
            style = BakeRoadTheme.typography.bodySmallSemibold.copy(color = BakeRoadTheme.colorScheme.Black)
        )
    }
}

/* Catmull-Rom Splines → Cubic Bezier */
private fun Path.catmullRom(points: List<Offset>, tension: Float) {
    /* tension: 곡률 (0~1) */
    if (points.size < 2) return
    moveTo(points.first().x, points.first().y)

    for (i in 0 until points.size - 1) {
        val p0 = points.getOrNull(i - 1) ?: points[i]
        val p1 = points[i]
        val p2 = points[i + 1]
        val p3 = points.getOrNull(i + 2) ?: p2
        val c1 = Offset(
            p1.x + (p2.x - p0.x) * tension / 6f,
            p1.y + (p2.y - p0.y) * tension / 6f
        )
        val c2 = Offset(
            p2.x - (p3.x - p1.x) * tension / 6f,
            p2.y - (p3.y - p1.y) * tension / 6f
        )

        cubicTo(c1.x, c1.y, c2.x, c2.y, p2.x, p2.y)
    }
}

/* Monotone Cubic → Cubic Bezier */
private fun Path.monotoneCubic(points: List<Offset>) {
    if (points.size < 2) return
    moveTo(points.first().x, points.first().y)

    val n = points.size
    val x = FloatArray(n) { points[it].x }
    val y = FloatArray(n) { points[it].y }

    // 구간 기울기 (secant slopes)
    val dx = FloatArray(n - 1)
    val dy = FloatArray(n - 1)
    val m = FloatArray(n - 1)
    for (i in 0 until n - 1) {
        dx[i] = x[i + 1] - x[i]
        dy[i] = y[i + 1] - y[i]
        m[i] = if (dx[i] != 0f) dy[i] / dx[i] else 0f
    }

    // 각 점에서의 접선 기울기 (tangent slopes)
    val t = FloatArray(n)
    t[0] = m[0]
    t[n - 1] = m[n - 2]
    for (i in 1 until n - 1) {
        // 인접 구간이 부호가 다르면(모노토닉 아님) 0으로 눌러 overshoot 방지
        if (m[i - 1] * m[i] <= 0f) t[i] = 0f
        else t[i] = (m[i - 1] + m[i]) / 2f
    }

    // Fritsch–Carlson 보정 (아주 급한 구간에서 둔화)
    for (i in 0 until n - 1) {
        if (m[i] == 0f) {
            t[i] = 0f
            t[i + 1] = 0f
            continue
        }
        val a = t[i] / m[i]
        val b = t[i + 1] / m[i]
        val s = a * a + b * b
        if (s > 9f) {
            val tau = 3f / kotlin.math.sqrt(s)
            t[i] = tau * a * m[i]
            t[i + 1] = tau * b * m[i]
        }
    }

    // 각 구간 Cubic Bezier 변환
    for (i in 0 until n - 1) {
        val x0 = x[i]
        val y0 = y[i]
        val x1 = x[i + 1]
        val y1 = y[i + 1]
        val h = dx[i]
        val c1 = Offset(x0 + h / 3f, y0 + t[i] * h / 3f)
        val c2 = Offset(x1 - h / 3f, y1 - t[i + 1] * h / 3f)

        cubicTo(c1.x, c1.y, c2.x, c2.y, x1, y1)
    }
}

@Preview
@Composable
private fun BreadSpendingPriceCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White)
    ) {
        BreadSpendingPriceCard(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            currentMonth = 8,
            priceList = persistentListOf(
                72000,
                215000,
                84000
            )
        )
    }
}

@Preview
@Composable
private fun BreadSpendingPriceChartPreview() {
    BreadSpendingPriceChart(
        modifier = Modifier.size(width = 164.dp, height = 85.dp),
        values = persistentListOf(0.55f, 0.4f, 1f)
    )
}