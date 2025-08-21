package com.twolskone.bakeroad.feature.report.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.report.R

/**
 * 이번달 내 빵글 활동 총 정리
 */
@Composable
internal fun ActivitySummaryCard(
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
        elevation = CardDefaults.cardElevation(CardElevation)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            ) {
                ActivitySummaryCell(
                    modifier = Modifier.weight(1f),
                    title = stringResource(id = R.string.feature_report_title_summary_review_count),
                    count = 1
                )
                VerticalDivider(
                    modifier = Modifier.padding(top = 16.dp),
                    color = BakeRoadTheme.colorScheme.Gray50
                )
                ActivitySummaryCell(
                    modifier = Modifier.weight(1f),
                    title = stringResource(id = R.string.feature_report_title_summary_bread_count),
                    count = 1
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp),
                color = BakeRoadTheme.colorScheme.Gray50
            )
            Row(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            ) {
                ActivitySummaryCell(
                    modifier = Modifier.weight(1f),
                    title = stringResource(id = R.string.feature_report_title_summary_like_given_count),
                    count = 1
                )
                VerticalDivider(
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = BakeRoadTheme.colorScheme.Gray50
                )
                ActivitySummaryCell(
                    modifier = Modifier.weight(1f),
                    title = stringResource(id = R.string.feature_report_title_summary_like_received_count),
                    count = 1
                )
            }
        }
    }
}

@Composable
private fun ActivitySummaryCell(
    modifier: Modifier,
    title: String,
    count: Int
) {
    Column(
        modifier = modifier.padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = BakeRoadTheme.typography.bodyXsmallSemibold
        )
        BakeRoadChip(
            modifier = Modifier.padding(top = 8.dp),
            selected = true,
            color = ChipColor.MAIN,
            size = ChipSize.LARGE,
            label = { Text(text = stringResource(id = R.string.feature_report_format_activity_count, count)) }
        )
    }
}

@Preview
@Composable
private fun ActivitySummaryCardPreview() {
    BakeRoadTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BakeRoadTheme.colorScheme.White)
        ) {
            ActivitySummaryCard(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 20.dp),
            )
        }
    }
}