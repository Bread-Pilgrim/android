package com.twolskone.bakeroad.feature.report.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBarIcon
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.type.DayOfWeek
import com.twolskone.bakeroad.feature.report.R
import com.twolskone.bakeroad.feature.report.component.ActivitySummaryCard
import com.twolskone.bakeroad.feature.report.component.AreaVisitRankCard
import com.twolskone.bakeroad.feature.report.component.BreadAveragePurchasesCard
import com.twolskone.bakeroad.feature.report.component.BreadConsumptionCard
import com.twolskone.bakeroad.feature.report.component.BreadSpendingAmountCard
import com.twolskone.bakeroad.feature.report.component.BreadType
import com.twolskone.bakeroad.feature.report.component.BreadTypeRankCard
import com.twolskone.bakeroad.feature.report.component.VisitedArea
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun ReportDetailScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White)
            .systemBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 12.dp)
        ) {
            BakeRoadTopAppBarIcon(
                modifier = Modifier.align(Alignment.CenterStart),
                iconRes = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_back,
                contentDescription = "Back",
                onClick = onBackClick
            )
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                BakeRoadTopAppBarIcon(
                    iconSize = 20.dp,
                    iconRes = R.drawable.feature_report_ic_left_arrow,
                    contentDescription = "LeftArrow",
                    tint = BakeRoadTheme.colorScheme.Gray600,
                    onClick = {}
                )
                Text(
                    text = "2025년 7월",
                    style = BakeRoadTheme.typography.bodyLargeSemibold,
                    color = BakeRoadTheme.colorScheme.Black
                )
                BakeRoadTopAppBarIcon(
                    iconSize = 20.dp,
                    iconRes = R.drawable.feature_report_ic_right_arrow,
                    contentDescription = "RightArrow",
                    tint = BakeRoadTheme.colorScheme.Gray600,
                    onClick = {}
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            // 자주 간 부산 지역.
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(id = R.string.feature_report_title_area_visit_rank),
                        style = BakeRoadTheme.typography.bodyMediumSemibold
                    )
                    AreaVisitRankCard(
                        modifier = Modifier.fillMaxWidth(),
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
                            )
                        )
                    )
                }
            }
            // 가장 많이 먹은 빵 종류.
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(id = R.string.feature_report_title_bread_type_rank),
                        style = BakeRoadTheme.typography.bodyMediumSemibold
                    )
                    BreadTypeRankCard(
                        modifier = Modifier.fillMaxWidth(),
                        breadTypeList = persistentListOf(
                            BreadType(
                                breadName = "페이스트리류",
                                eatenCount = 2
                            ),
                            BreadType(
                                breadName = "클래식 & 레트로 빵",
                                eatenCount = 2
                            ),
                            BreadType(
                                breadName = "구운과자류",
                                eatenCount = 1
                            )
                        )
                    )
                }
            }
            // 하루 평균 빵 구매량.
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(id = R.string.feature_report_title_bread_average_purchases),
                        style = BakeRoadTheme.typography.bodyMediumSemibold
                    )
                    BreadAveragePurchasesCard(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            // 이번 달 빵 소비 금액.
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(id = R.string.feature_report_title_bread_spending_amount),
                        style = BakeRoadTheme.typography.bodyMediumSemibold
                    )
                    BreadSpendingAmountCard(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            // 요일별 빵 섭취 패턴.
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(id = R.string.feature_report_title_bread_consumption),
                        style = BakeRoadTheme.typography.bodyMediumSemibold
                    )
                    BreadConsumptionCard(
                        modifier = Modifier.fillMaxWidth(),
                        data = DayOfWeek.entries.map {
                            it to (0..5).random()
                        }.toImmutableList()
                    )
                }
            }
            // 이번 달 내 빵글 활동 총정리.
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(id = R.string.feature_report_title_activity_summary),
                        style = BakeRoadTheme.typography.bodyMediumSemibold
                    )
                    ActivitySummaryCard(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ReportDetailScreenPreview() {
    BakeRoadTheme {
        ReportDetailScreen(
            onBackClick = {}
        )
    }
}