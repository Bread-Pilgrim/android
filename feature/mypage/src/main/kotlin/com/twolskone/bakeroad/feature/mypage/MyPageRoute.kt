package com.twolskone.bakeroad.feature.mypage

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import com.twolskone.bakeroad.feature.mypage.model.Menu
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
internal fun MyPageRoute(
    padding: PaddingValues,
    viewModel: MyPageViewModel = hiltViewModel(),
    navigateToSettings: () -> Unit,
    navigateToReport: () -> Unit,
    navigateToEditPreference: (ActivityResultLauncher<Intent>) -> Unit,
    goBack: () -> Unit,
    showSnackbar: (SnackbarState) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val changePreferenceLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Timber.i("xxx bakeryDetailLauncher :: resultCode ${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.mainTabEventBus.setHomeRefreshState(value = true)
        }
    }

    BackHandler { goBack() }

    LaunchedEffect(Unit) {
        viewModel.snackbarEffect.collectLatest { state ->
            showSnackbar(state)
        }
    }

    MyPageScreen(
        padding = padding,
        state = state,
        onSettingsClick = navigateToSettings,
        onBadgeSettingsClick = {},
        onMenuClick = { menu ->
            when (menu) {
                Menu.Report -> navigateToReport()
                Menu.Badge -> TODO()
                Menu.Review -> TODO()
                Menu.Preference -> navigateToEditPreference(changePreferenceLauncher)
            }
        }
    )
}