package com.twolskone.bakeroad.feature.settings.notice

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.common.android.base.BaseComposable

@Composable
internal fun NoticeRoute(
    viewModel: NoticeViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BaseComposable(baseViewModel = viewModel) {
        NoticeScreen(
            state = state,
            onBackClick = goBack
        )
    }
}