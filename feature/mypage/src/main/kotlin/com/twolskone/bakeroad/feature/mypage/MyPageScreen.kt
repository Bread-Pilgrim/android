package com.twolskone.bakeroad.feature.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadTextButton
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBarIcon
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.ui.ProfileImage
import com.twolskone.bakeroad.feature.mypage.model.Menu

private val MyPageSectionShape = RoundedCornerShape(20.dp)

@Composable
internal fun MyPageScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    onSettingsClick: () -> Unit,
    onBadgeSettingsClick: () -> Unit,
    onMenuClick: (Menu) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White)
            .padding(bottom = padding.calculateBottomPadding())
            .verticalScroll(rememberScrollState())
    ) {
        BakeRoadTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            rightActions = {
                BakeRoadTopAppBarIcon(
                    iconRes = R.drawable.feature_mypage_ic_settings,
                    contentDescription = "Settings",
                    onClick = onSettingsClick
                )
            }
        )
        ProfileSection(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        MenuListSection(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            onClick = onMenuClick
        )
    }
}

@Composable
private fun ProfileSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = BakeRoadTheme.colorScheme.Gray40, shape = MyPageSectionShape)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProfileImage(
            size = 56.dp,
            profileUrl = ""
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "김빵글",
                style = BakeRoadTheme.typography.bodyLargeSemibold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "대표뱃지 미설정",
                    style = BakeRoadTheme.typography.bodyXsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray600)
                )
                BakeRoadTextButton(
                    style = TextButtonStyle.ASSISTIVE,
                    size = TextButtonSize.SMALL,
                    onClick = {},
                    content = {
                        Text(
                            text = stringResource(id = R.string.feature_mypage_button_badge_settings),
                            style = BakeRoadTheme.typography.bodySmallSemibold.copy(textDecoration = TextDecoration.Underline)
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun MenuListSection(
    modifier: Modifier = Modifier,
    onClick: (Menu) -> Unit
) {
    Column(modifier = modifier.background(color = BakeRoadTheme.colorScheme.Gray40, shape = MyPageSectionShape)) {
        Menu.entries.fastForEachIndexed { i, menu ->
            Row(
                modifier = Modifier
                    .singleClickable { onClick(menu) }
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = menu.labelRes),
                    style = BakeRoadTheme.typography.bodyMediumSemibold.copy(color = BakeRoadTheme.colorScheme.Gray900)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_right_arrow),
                    contentDescription = "RightArrow",
                    tint = BakeRoadTheme.colorScheme.Gray500
                )
            }
            if (Menu.entries.lastIndex != i) {
                HorizontalDivider(color = BakeRoadTheme.colorScheme.Gray50)
            }
        }
    }
}

@Preview
@Composable
private fun MyPageScreenPreview() {
    BakeRoadTheme {
        MyPageScreen(
            modifier = Modifier.fillMaxSize(),
            padding = PaddingValues(),
            onSettingsClick = {},
            onBadgeSettingsClick = {},
            onMenuClick = {}
        )
    }
}