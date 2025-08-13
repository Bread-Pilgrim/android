package com.twolskone.bakeroad.feature.report.component

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

/**
 * 하루 평균 빵 구매량 카드
 */
@Composable
fun BreadAveragePurchasesCard(
    modifier: Modifier = Modifier
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
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val avgCount = 3.32.toString()
            val rawTitle = stringResource(R.string.feature_report_title_bread_average_purchase_count, avgCount)
            val annotatedTitle = buildAnnotatedString {
                append(rawTitle)
                val start = rawTitle.indexOf(avgCount)
                if (start >= 0) {
                    addStyle(
                        style = BakeRoadTheme.typography.headingSmallBold.copy(color = BakeRoadTheme.colorScheme.Primary500).toSpanStyle(),
                        start = start,
                        end = start + avgCount.length
                    )
                }
            }
            Text(
                text = annotatedTitle,
                style = BakeRoadTheme.typography.bodyMediumSemibold
            )

            val moreCount = 2
            val rawMoreCount = stringResource(R.string.feature_report_format_bread_count, moreCount)
            val rawDescription = stringResource(id = R.string.feature_report_description_daily_bread_average_count, rawMoreCount)
            val annotatedDescription = buildAnnotatedString {
                append(rawDescription)
                val start = rawDescription.indexOf(rawMoreCount)
                if (start >= 0) {
                    addStyle(
                        style = BakeRoadTheme.typography.bodyXsmallSemibold.toSpanStyle(),
                        start = start,
                        end = start + rawMoreCount.length
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
                val count = 10
                val day = 3
                val rawCount = stringResource(id = R.string.feature_report_format_visited_count, count)
                val rawDay = stringResource(id = R.string.feature_report_format_day, day)
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
private fun BreadAveragePurchasesCardPreview() {
    BakeRoadTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BakeRoadTheme.colorScheme.White)
        ) {
            BreadAveragePurchasesCard(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            )
        }
    }
}