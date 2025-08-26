package com.twolskone.bakeroad.feature.badge

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.core.navigator.util.RESULT_REFRESH_PROFILE
import com.twolskone.bakeroad.feature.badge.mvi.BadgeListIntent

@Composable
internal fun BadgeListRoute(
    viewModel: BadgeListViewModel = hiltViewModel(),
    setResult: (code: Int, withFinish: Boolean) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler {
        setResult(if (state.isBadgeUpdated) RESULT_REFRESH_PROFILE else Activity.RESULT_OK, true)
    }

    BaseComposable(baseViewModel = viewModel) {
        BadgeListScreen(
            state = state,
            onBackClick = { setResult(if (state.isBadgeUpdated) RESULT_REFRESH_PROFILE else Activity.RESULT_OK, true) },
            onEnableBadge = { badge -> viewModel.intent(BadgeListIntent.EnableBadge(badge)) },
            onDisableBadge = { badge -> viewModel.intent(BadgeListIntent.DisableBadge(badge)) }
        )
    }
}