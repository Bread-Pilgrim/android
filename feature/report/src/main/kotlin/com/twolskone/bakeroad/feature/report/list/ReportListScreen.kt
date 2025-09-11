package com.twolskone.bakeroad.feature.report.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.analytics.LogComposeScreenEvent
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBarIcon
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.ui.EmptyCard
import com.twolskone.bakeroad.feature.report.R
import com.twolskone.bakeroad.feature.report.list.mvi.ReportListState

@Composable
internal fun ReportListScreen(
    modifier: Modifier = Modifier,
    state: ReportListState,
    listState: LazyListState,
    onBackClick: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    LogComposeScreenEvent(screen = "ReportListScreen")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White)
            .systemBarsPadding()
    ) {
        BakeRoadTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            leftActions = {
                BakeRoadTopAppBarIcon(
                    iconRes = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_back,
                    contentDescription = "Back",
                    onClick = onBackClick
                )
            },
            title = { Text(text = stringResource(id = R.string.feature_report)) }
        )
        if (state.paging.list.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = listState
            ) {
                itemsIndexed(
                    items = state.paging.list,
                    key = { _, date -> "${date.year}/${date.month}" }
                ) { index, date ->
                    ReportListItem(
                        date = stringResource(id = R.string.feature_report_format_monthly_list, date.year, date.month),
                        onClick = { onItemClick(index) }
                    )
                    if (state.paging.list.lastIndex != index) {
                        HorizontalDivider(color = BakeRoadTheme.colorScheme.Gray50)
                    }
                }
            }
        } else {
            EmptyCard(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                description = stringResource(id = R.string.feature_report_empty_list)
            )
        }
    }
}

@Composable
private fun ReportListItem(
    modifier: Modifier = Modifier,
    date: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .singleClickable { onClick() }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = date,
            style = BakeRoadTheme.typography.bodyMediumSemibold,
            color = BakeRoadTheme.colorScheme.Gray900
        )
        Icon(
            imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_right_arrow),
            contentDescription = "RightArrow",
            tint = BakeRoadTheme.colorScheme.Gray500
        )
    }
}

@Preview
@Composable
private fun ReportListScreenPreview() {
    BakeRoadTheme {
        ReportListScreen(
            state = ReportListState(),
            listState = rememberLazyListState(),
            onBackClick = {},
            onItemClick = {}
        )
    }
}