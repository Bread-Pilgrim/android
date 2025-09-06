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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.loading.BakeRoadLoadingScreen
import com.twolskone.bakeroad.core.designsystem.component.loading.LoadingType
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBarIcon
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.report.R
import com.twolskone.bakeroad.feature.report.detail.component.ActivitySummaryCard
import com.twolskone.bakeroad.feature.report.detail.component.BreadAverageCountCard
import com.twolskone.bakeroad.feature.report.detail.component.BreadSpendingPriceCard
import com.twolskone.bakeroad.feature.report.detail.component.BreadTypeRankCard
import com.twolskone.bakeroad.feature.report.detail.component.BreadWeeklyDistributionCard
import com.twolskone.bakeroad.feature.report.detail.component.VisitedAreaRankCard
import com.twolskone.bakeroad.feature.report.detail.mvi.ReportDetailState
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun ReportDetailScreen(
    modifier: Modifier = Modifier,
    state: ReportDetailState,
    onBackClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
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
            state.currentDate?.takeIf { it.year > 0 && it.month > 0 }?.let { date ->
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (state.hasPrevious) {
                        BakeRoadTopAppBarIcon(
                            iconSize = 20.dp,
                            iconRes = R.drawable.feature_report_ic_left_arrow,
                            contentDescription = "LeftArrow",
                            tint = BakeRoadTheme.colorScheme.Gray600,
                            onClick = onPreviousClick
                        )
                    } else {
                        Box(modifier = Modifier.size(28.dp))
                    }
                    Text(
                        text = stringResource(id = R.string.feature_report_format_monthly_list, date.year, date.month),
                        style = BakeRoadTheme.typography.bodyLargeSemibold,
                        color = BakeRoadTheme.colorScheme.Black
                    )
                    if (state.hasNext) {
                        BakeRoadTopAppBarIcon(
                            iconSize = 20.dp,
                            iconRes = R.drawable.feature_report_ic_right_arrow,
                            contentDescription = "RightArrow",
                            tint = BakeRoadTheme.colorScheme.Gray600,
                            onClick = onNextClick
                        )
                    } else {
                        Box(modifier = Modifier.size(28.dp))
                    }
                }
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
                    VisitedAreaRankCard(
                        modifier = Modifier.fillMaxWidth(),
                        areaList = state.data.visitedAreas.toImmutableList()
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
                        breadTypeList = state.data.breadTypes.toImmutableList()
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
                    BreadAverageCountCard(
                        modifier = Modifier.fillMaxWidth(),
                        averageCount = state.data.breadDailyAverageCount,
                        gapCount = state.data.breadMonthlyConsumptionGap,
                        totalCount = state.data.totalBreadCount,
                        totalVisitedCount = state.data.totalVisitedCount
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
                    BreadSpendingPriceCard(
                        modifier = Modifier.fillMaxWidth(),
                        currentMonth = state.data.month,
                        priceList = state.data.totalPrices.toImmutableList()
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
                    BreadWeeklyDistributionCard(
                        modifier = Modifier.fillMaxWidth(),
                        data = state.data.breadWeeklyDistribution
                            .toList()
                            .sortedBy { (dayOfWeek, _) -> dayOfWeek.value }
                            .toImmutableList()
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
                        modifier = Modifier.fillMaxWidth(),
                        reviewCount = state.data.reviewCount,
                        breadCount = state.data.totalBreadCount,
                        sentLikeCount = state.data.sentLikeCount,
                        receivedLikeCount = state.data.receivedLikeCount
                    )
                }
            }
        }
    }

    BakeRoadLoadingScreen(
        modifier = Modifier.fillMaxSize(),
        isLoading = state.loading,
        type = LoadingType.Default
    )
}

@Preview
@Composable
private fun ReportDetailScreenPreview() {
    BakeRoadTheme {
        ReportDetailScreen(
            state = ReportDetailState(),
            onBackClick = {},
            onPreviousClick = {},
            onNextClick = {}
        )
    }
}