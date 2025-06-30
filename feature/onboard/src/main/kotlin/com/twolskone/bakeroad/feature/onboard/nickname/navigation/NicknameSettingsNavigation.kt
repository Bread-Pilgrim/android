package com.twolskone.bakeroad.feature.onboard.nickname.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.onboard.nickname.NicknameSettingsRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object NicknameSettingsRoute

internal fun NavGraphBuilder.nicknameSettingsScreen(
    onBackClick: () -> Unit,
) {
    composable<NicknameSettingsRoute> {
        NicknameSettingsRoute(
            onBackClick = onBackClick
        )
    }
}