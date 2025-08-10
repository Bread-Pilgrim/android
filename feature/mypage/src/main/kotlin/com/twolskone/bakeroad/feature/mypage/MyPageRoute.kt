package com.twolskone.bakeroad.feature.mypage

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import com.twolskone.bakeroad.feature.mypage.model.Menu
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun MyPageRoute(
    padding: PaddingValues,
    viewModel: MyPageViewModel = hiltViewModel(),
    navigateToSettings: () -> Unit,
    showSnackbar: (SnackbarState) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.snackbarEffect.collectLatest { state ->
            showSnackbar(state)
        }
    }

    MyPageScreen(
        padding = padding,
        onSettingsClick = navigateToSettings,
        onBadgeSettingsClick = {},
        onMenuClick = { menu ->
            when (menu) {
                Menu.Settlement -> TODO()
                Menu.Badge -> TODO()
                Menu.Review -> TODO()
                Menu.Preference -> TODO()
            }
        }
    )
}