package com.twolskone.bakeroad.feature.settings.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBarIcon
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.settings.R

@Composable
internal fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onNoticeClick: () -> Unit,
    onAppInfoClick: () -> Unit,
    onLogoutClick: () -> Unit
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
            title = { Text(text = stringResource(id = R.string.feature_settings)) }
        )
        SettingsMenu(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.feature_settings_notice),
            onClick = onNoticeClick
        )
        HorizontalDivider(color = BakeRoadTheme.colorScheme.Gray50)
        SettingsMenu(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.feature_settings_app_info),
            onClick = onAppInfoClick
        )
        HorizontalDivider(color = BakeRoadTheme.colorScheme.Gray50)
        SettingsMenu(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.feature_settings_label_logout),
            textStyle = BakeRoadTheme.typography.bodyMediumMedium.copy(color = BakeRoadTheme.colorScheme.Gray400),
            showRightArrow = false,
            onClick = {}
        )
    }
}

@Composable
private fun SettingsMenu(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = BakeRoadTheme.typography.bodyMediumSemibold.copy(color = BakeRoadTheme.colorScheme.Gray900),
    showRightArrow: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .singleClickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ProvideTextStyle(textStyle) {
            Text(
                modifier = Modifier.weight(1f),
                text = text
            )
        }
        if (showRightArrow) {
            Icon(
                modifier = Modifier.padding(start = 16.dp),
                imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_right_arrow),
                contentDescription = "RightArrow",
                tint = BakeRoadTheme.colorScheme.Gray500
            )
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    BakeRoadTheme {
        SettingsScreen(
            onBackClick = {},
            onNoticeClick = {},
            onAppInfoClick = {},
            onLogoutClick = {}
        )
    }
}