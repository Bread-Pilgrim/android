package com.twolskone.bakeroad.feature.report.detail.component

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.report.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal val CardShape = RoundedCornerShape(20.dp)
internal val CardElevation = 4.5.dp

private const val AreaMaxCount = 3

/**
 * 자주 간 부산 지역 순위카드
 * @param areaList  지역 목록 (지역명:횟수)
 */
@Composable
internal fun VisitedAreaRankCard(
    modifier: Modifier = Modifier,
    areaList: ImmutableList<Pair<String, Int>>
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
        if (areaList.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UnderlineText(
                    text = stringResource(id = R.string.feature_report_empty_area_visit_rank_1),
                    textStyle = BakeRoadTheme.typography.bodyXsmallMedium
                )
                Text(
                    text = stringResource(id = R.string.feature_report_empty_area_visit_rank_2),
                    style = BakeRoadTheme.typography.bodyXsmallMedium
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                areaList
                    .take(AreaMaxCount)
                    .fastForEachIndexed { i, (areaName, count) ->
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            UnderlineText(
                                text = stringResource(id = R.string.feature_report_format_rank, i + 1),
                                textStyle = BakeRoadTheme.typography.bodyXsmallMedium
                            )
                            Text(
                                text = areaName,
                                style = BakeRoadTheme.typography.bodyMediumSemibold
                            )
                            BakeRoadChip(
                                selected = true,
                                color = ChipColor.MAIN,
                                size = ChipSize.LARGE,
                                label = { Text(text = stringResource(id = R.string.feature_report_format_visited_count, count)) }
                            )
                        }
                        if (i < areaList.lastIndex) {
                            VerticalDivider(
                                color = BakeRoadTheme.colorScheme.Gray50
                            )
                        }
                    }
            }
        }
    }
}

@Preview
@Composable
private fun VisitedAreaRankCardPreview() {
    BakeRoadTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BakeRoadTheme.colorScheme.White)
        ) {
            VisitedAreaRankCard(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                areaList = persistentListOf(
                    "남포•광복" to 1,
                    "연산•거제 " to 1,
                    "광안리•민락" to 3
                )
            )
        }
    }
}
