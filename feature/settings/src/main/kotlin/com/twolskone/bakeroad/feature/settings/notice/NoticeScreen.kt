package com.twolskone.bakeroad.feature.settings.notice

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBarIcon
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.settings.R

@Composable
internal fun NoticeScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
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
            title = { Text(text = stringResource(id = R.string.feature_settings_notice)) }
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(5) {
                NoticeItem(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun NoticeItem(
    modifier: Modifier = Modifier
) {
    var isExpand by remember { mutableStateOf(false) }
    val arrowIconAngle by animateFloatAsState(
        targetValue = if (isExpand) -180f else 0f,
        label = "ArrowIconRotationAnimation"
    )

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .singleClickable { isExpand = !isExpand }
                .animateContentSize()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "성수동 인기 빵집 정보 업데이트 안내",
                    style = BakeRoadTheme.typography.bodyMediumSemibold,
                    color = BakeRoadTheme.colorScheme.Gray900
                )
                Icon(
                    modifier = Modifier.rotate(arrowIconAngle),
                    imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_down_arrow),
                    contentDescription = null,
                    tint = BakeRoadTheme.colorScheme.Gray500
                )
            }
            if (isExpand) {
                Text(
                    modifier = Modifier
                        .padding(top = 12.dp, end = 44.dp)
                        .fillMaxWidth(),
                    text = "최근 성수동 지역의 인기 베이커리 정보를 새롭게 반영하였습니다.\n" +
                            "주말 방문 전, 최신 운영시간과 메뉴 정보를 확인해 주세요.",
                    style = BakeRoadTheme.typography.bodyXsmallRegular,
                    color = BakeRoadTheme.colorScheme.Gray800
                )
            }
        }
        HorizontalDivider(color = BakeRoadTheme.colorScheme.Gray50)
    }
}

@Preview
@Composable
private fun NoticeScreenPreview() {
    BakeRoadTheme {
        NoticeScreen(
            modifier = Modifier.fillMaxSize(),
            onBackClick = {}
        )
    }
}