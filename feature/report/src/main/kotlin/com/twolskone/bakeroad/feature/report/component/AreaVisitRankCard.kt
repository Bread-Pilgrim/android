package com.twolskone.bakeroad.feature.report.component

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
import androidx.compose.runtime.Immutable
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

private const val AreaMaxCount = 3

/**
 * 자주 간 부산 지역 순위카드
 */
@Composable
internal fun AreaVisitRankCard(
    modifier: Modifier = Modifier,
    regionList: ImmutableList<VisitedArea>
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
        if (regionList.isEmpty()) {
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
                regionList
                    .take(AreaMaxCount)
                    .fastForEachIndexed { i, region ->
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
                                text = region.name,
                                style = BakeRoadTheme.typography.bodyMediumSemibold
                            )
                            BakeRoadChip(
                                selected = true,
                                color = ChipColor.MAIN,
                                size = ChipSize.LARGE,
                                label = { Text(text = stringResource(id = R.string.feature_report_format_visited_count, region.visitedCount)) }
                            )
                        }
                        if (i < regionList.lastIndex) {
                            VerticalDivider(
                                color = BakeRoadTheme.colorScheme.Gray50
                            )
                        }
                    }
            }
        }
    }
}

// Temporary model.
@Immutable
internal data class VisitedArea(
    val name: String,
    val visitedCount: Int
)

@Preview
@Composable
private fun AreaVisitRankCardPreview() {
    BakeRoadTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BakeRoadTheme.colorScheme.White)
        ) {
            AreaVisitRankCard(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                regionList = persistentListOf(
                    VisitedArea(
                        name = "해운대구",
                        visitedCount = 3
                    ),
                    VisitedArea(
                        name = "남구",
                        visitedCount = 2
                    ),
                    VisitedArea(
                        name = "수영구",
                        visitedCount = 1
                    ),
                )
            )
        }
    }
}
