package com.twolskone.bakeroad.core.ui.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonRole
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonRole
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.Badge
import com.twolskone.bakeroad.core.ui.BakeRoadBadge
import com.twolskone.bakeroad.core.ui.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * 뱃지 획득 바텀시트
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgeAchievedBottomSheet(
    modifier: Modifier = Modifier,
    badgeList: ImmutableList<Badge>,
    onDismissRequest: () -> Unit,
    onBadgeSettingsClick: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { badgeList.size }
    )

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = BakeRoadTheme.colorScheme.White,
        contentColor = BakeRoadTheme.colorScheme.Gray990,
        tonalElevation = 2.dp,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BakeRoadTheme.colorScheme.White)
                .padding(top = 2.dp)
                .padding(horizontal = 16.dp, vertical = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (badgeList.size > 1) {
                    stringResource(id = R.string.core_ui_title_badge_achieved_multiple, badgeList.size)
                } else {
                    stringResource(id = R.string.core_ui_title_badge_achieved_single)
                },
                style = BakeRoadTheme.typography.headingSmallBold,
                color = BakeRoadTheme.colorScheme.Primary500,
                textAlign = TextAlign.Center
            )
            HorizontalPager(
                modifier = Modifier.fillMaxWidth(),
                state = pagerState,
            ) { index ->
                badgeList.getOrNull(index)?.let {
                    BadgeContent(
                        modifier = Modifier.fillMaxWidth(),
                        badge = it
                    )
                }
            }
            if (badgeList.size > 1) {
                PagerIndicator(
                    modifier = Modifier.padding(bottom = 24.dp),
                    index = pagerState.currentPage,
                    size = pagerState.pageCount
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                BakeRoadOutlinedButton(
                    modifier = Modifier.weight(1f),
                    role = OutlinedButtonRole.SECONDARY,
                    size = ButtonSize.LARGE,
                    onClick = onDismissRequest,
                    content = { Text(text = stringResource(id = R.string.core_ui_button_close)) }
                )
                BakeRoadSolidButton(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    role = SolidButtonRole.PRIMARY,
                    size = ButtonSize.LARGE,
                    onClick = onBadgeSettingsClick,
                    content = { Text(text = stringResource(id = R.string.core_ui_button_badge_settings)) }
                )
            }
        }
    }
}

@Composable
private fun BadgeContent(
    modifier: Modifier = Modifier,
    badge: Badge
) {
    Column(
        modifier = modifier.padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BakeRoadBadge(
            size = 90.dp,
            imageSize = 72.dp,
            badge = badge
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = badge.name,
            style = BakeRoadTheme.typography.bodyMediumSemibold
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = badge.description,
            style = BakeRoadTheme.typography.bodyXsmallMedium,
            color = BakeRoadTheme.colorScheme.Gray600,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PagerIndicator(
    modifier: Modifier = Modifier,
    index: Int,
    size: Int
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        (0 until size).forEach { i ->
            val color = if (i == index) BakeRoadTheme.colorScheme.Primary500 else BakeRoadTheme.colorScheme.Gray100
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color = color, shape = CircleShape)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BadgeAchievedBottomSheetPreview() {
    BakeRoadTheme {
        var showSheet by remember { mutableStateOf(true) }

        if (showSheet) {
            BadgeAchievedBottomSheet(
                modifier = Modifier.fillMaxWidth(),
                badgeList = persistentListOf(
                    Badge(
                        id = 1,
                        name = "새싹 빵러",
                        description = "처음이 소중해요~ 빵지순례 시작을 축하해요!",
                        imageUrl = "",
                        isEarned = true,
                        isRepresentative = false
                    ),
//                    Badge(
//                        id = 1,
//                        name = "새싹 빵러",
//                        description = "처음이 소중해요~ 빵지순례 시작을 축하해요!",
//                        imageUrl = "",
//                        isEarned = true,
//                        isRepresentative = false
//                    )
                ),
                onDismissRequest = { showSheet = false },
                onBadgeSettingsClick = { showSheet = false }
            )
        }
    }
}