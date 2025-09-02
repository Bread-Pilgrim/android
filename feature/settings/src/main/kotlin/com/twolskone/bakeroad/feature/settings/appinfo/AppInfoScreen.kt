package com.twolskone.bakeroad.feature.settings.appinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.common.android.util.DeviceUtil
import com.twolskone.bakeroad.core.common.android.util.DeviceUtil.appVersion
import com.twolskone.bakeroad.core.common.android.util.DeviceUtil.appVersionCode
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBarIcon
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.settings.R

private val InfoPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp)
private val InfoTextStyle: TextStyle
    @Composable
    get() = BakeRoadTheme.typography.bodyXsmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray800)

@Composable
internal fun AppInfoScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

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
            title = { Text(text = stringResource(id = R.string.feature_settings_app_info)) }
        )
        // 장치 정보
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(InfoPadding)
        ) {
            Text(
                text = stringResource(id = R.string.feature_settings_title_device_info),
                style = BakeRoadTheme.typography.bodyMediumSemibold.copy(color = BakeRoadTheme.colorScheme.Gray900)
            )
            ProvideTextStyle(InfoTextStyle) {
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = "${DeviceUtil.manufacturer} ${DeviceUtil.model} (${DeviceUtil.osVersion})"
                )
            }
        }
        HorizontalDivider(color = BakeRoadTheme.colorScheme.Gray50)
        // 앱 버전
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(InfoPadding)
        ) {
            Text(
                text = stringResource(id = R.string.feature_settings_title_app_version),
                style = BakeRoadTheme.typography.bodyMediumSemibold.copy(color = BakeRoadTheme.colorScheme.Gray900)
            )
            ProvideTextStyle(InfoTextStyle) {
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = stringResource(id = R.string.feature_settings_label_app_version, "${context.appVersion} (${context.appVersionCode})")
                )
            }
        }
        HorizontalDivider(color = BakeRoadTheme.colorScheme.Gray50)
        // 사용자 정보
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(InfoPadding)
//        ) {
//            Text(
//                text = stringResource(id = R.string.feature_settings_title_user_info),
//                style = BakeRoadTheme.typography.bodyMediumSemibold.copy(color = BakeRoadTheme.colorScheme.Gray900)
//            )
//            ProvideTextStyle(InfoTextStyle) {
//                Text(
//                    modifier = Modifier.padding(top = 12.dp),
//                    text = ""
//                )
//                Text(
//                    modifier = Modifier.padding(top = 8.dp),
//                    text = ""
//                )
//                Text(
//                    modifier = Modifier.padding(top = 8.dp),
//                    text = ""
//                )
//            }
//        }
    }
}


@Preview
@Composable
private fun AppInfoScreenPreview() {
    BakeRoadTheme {
        AppInfoScreen(
            onBackClick = {}
        )
    }
}
