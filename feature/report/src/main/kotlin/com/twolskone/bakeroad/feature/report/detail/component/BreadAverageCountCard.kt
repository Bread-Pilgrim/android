package com.twolskone.bakeroad.feature.report.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.report.R
import kotlin.math.abs

/**
 * 하루 평균 빵 구매량 카드
 * @param averageCount      하루 평균 빵 구매 개수
 * @param gapCount          다른 전체 사용자와의 구매 개수 차이
 * @param totalCount        이번달 구매한 총 빵 개수
 * @param totalVisitedCount 이번달 방문한 총 횟수
 */
@Composable
internal fun BreadAverageCountCard(
    modifier: Modifier = Modifier,
    averageCount: Float,
    gapCount: Float,
    totalCount: Int,
    totalVisitedCount: Int
) {
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
            val average = averageCount.toString()
            val rawTitle = stringResource(R.string.feature_report_title_bread_average_purchase_count, average)
            val annotatedTitle = buildAnnotatedString {
                append(rawTitle)
                val start = rawTitle.indexOf(average)
                if (start >= 0) {
                    addStyle(
                        style = BakeRoadTheme.typography.headingSmallBold.copy(color = BakeRoadTheme.colorScheme.Primary500).toSpanStyle(),
                        start = start,
                        end = start + average.length
                    )
                }
            }
            Text(
                text = annotatedTitle,
                style = BakeRoadTheme.typography.bodyMediumSemibold
            )

            val gap = stringResource(R.string.feature_report_format_bread_count, abs(gapCount).toString())
            val rawDescription = if (gapCount >= 0) {
                stringResource(id = R.string.feature_report_description_daily_bread_more_count, gap)
            } else {
                stringResource(id = R.string.feature_report_description_daily_bread_less_count, gap)
            }
            val annotatedDescription = buildAnnotatedString {
                append(rawDescription)
                val start = rawDescription.indexOf(gap)
                if (start >= 0) {
                    addStyle(
                        style = BakeRoadTheme.typography.bodyXsmallSemibold.toSpanStyle(),
                        start = start,
                        end = start + gap.length
                    )
                }
            }
            UnderlineText(
                modifier = Modifier.padding(top = 6.dp),
                annotatedText = annotatedDescription,
                textStyle = BakeRoadTheme.typography.bodyXsmallMedium
            )

            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(color = BakeRoadTheme.colorScheme.Primary500, shape = CircleShape)
                )
                Text(
                    text = stringResource(id = R.string.feature_report_label_total_this_month),
                    style = BakeRoadTheme.typography.bodyXsmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray600)
                )
                val rawCount = stringResource(id = R.string.feature_report_format_visited_count, totalCount)
                val rawDay = stringResource(id = R.string.feature_report_format_day, totalVisitedCount)
                val annotatedCountAndDay = buildAnnotatedString {
                    withStyle(style = BakeRoadTheme.typography.bodyXsmallSemibold.toSpanStyle().copy(color = BakeRoadTheme.colorScheme.Gray600)) {
                        append(rawCount)
                    }
                    append(" / ")
                    withStyle(style = BakeRoadTheme.typography.bodyXsmallSemibold.toSpanStyle().copy(color = BakeRoadTheme.colorScheme.Gray600)) {
                        append(rawDay)
                    }
                }
                Text(text = annotatedCountAndDay)
            }
        }
    }
}

@Preview
@Composable
private fun BreadAverageCountCardPreview() {
    BakeRoadTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BakeRoadTheme.colorScheme.White)
        ) {
            BreadAverageCountCard(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                averageCount = 3.32f,
                gapCount = 2f,
                totalCount = 10,
                totalVisitedCount = 3
            )
        }
    }
}