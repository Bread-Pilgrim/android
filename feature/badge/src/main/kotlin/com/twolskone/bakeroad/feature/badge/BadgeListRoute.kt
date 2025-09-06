package com.twolskone.bakeroad.feature.badge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.feature.badge.mvi.BadgeListIntent

@Composable
internal fun BadgeListRoute(
    viewModel: BadgeListViewModel = hiltViewModel(),
    finish: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BaseComposable(baseViewModel = viewModel) {
        BadgeListScreen(
            state = state,
            onBackClick = { finish() },
            onEnableBadge = { badge -> viewModel.intent(BadgeListIntent.EnableBadge(badge)) },
            onDisableBadge = { badge -> viewModel.intent(BadgeListIntent.DisableBadge(badge)) }
        )
    }
}