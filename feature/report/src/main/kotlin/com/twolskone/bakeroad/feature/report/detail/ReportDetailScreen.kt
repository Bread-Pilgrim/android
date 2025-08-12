package com.twolskone.bakeroad.feature.report.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBarIcon
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.report.R

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