package com.twolskone.bakeroad.feature.home

import android.app.Activity
import android.content.Intent
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
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.core.navigator.model.RESULT_REFRESH_BAKERY_LIST
import com.twolskone.bakeroad.feature.home.mvi.HomeIntent
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
internal fun HomeRoute(
    padding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToBakeryList: (areaCodes: String, BakeryType, launcher: ActivityResultLauncher<Intent>) -> Unit,
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int, launcher: ActivityResultLauncher<Intent>) -> Unit,
    navigateToEditPreference: (ActivityResultLauncher<Intent>) -> Unit,
    showSnackbar: (SnackbarState) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val changePreferenceLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Timber.i("xxx changePreferenceLauncher :: Refresh bakery list with snackbar")
            viewModel.intent(
                HomeIntent.RefreshBakeries(
                    completeSnackbarState = SnackbarState(
                        type = SnackbarType.SUCCESS,
                        messageRes = R.string.feature_home_snackbar_complete_edit_preference,
                    )
                )
            )
        } else {
            Timber.i("xxx changePreferenceLauncher :: Canceled change preference")
        }
    }
    val bakeryListLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_REFRESH_BAKERY_LIST) {
            Timber.i("xxx bakeryListLauncher :: Refresh bakery list")
            viewModel.intent(HomeIntent.RefreshBakeries())
        } else {
            Timber.i("xxx bakeryListLauncher :: Canceled")
        }
    }
    val bakeryDetailLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_REFRESH_BAKERY_LIST) {
            Timber.i("xxx bakeryDetailLauncher :: Refresh bakery list")
            viewModel.intent(HomeIntent.RefreshBakeries())
        } else {
            Timber.i("xxx bakeryDetailLauncher :: Canceled")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.snackbarEffect.collectLatest { state ->
            showSnackbar(state)
        }
    }

    HomeScreen(
        padding = padding,
        state = state,
        onAreaSelect = { selected, code -> viewModel.intent(HomeIntent.SelectArea(selected = selected, areaCode = code)) },
        onTourCategorySelect = { selected, category -> viewModel.intent(HomeIntent.SelectTourAreaCategory(selected = selected, category = category)) },
        onSeeAllBakeriesClick = { bakeryType ->
            navigateToBakeryList(
                /* areaCode*/ state.selectedAreaCodes.joinToString(separator = ","),
                /* bakeryType*/ bakeryType,
                /* launcher*/ bakeryListLauncher
            )
        },
        onBakeryClick = { bakery -> navigateToBakeryDetail(bakery.id, bakery.areaCode, bakeryDetailLauncher) },
        onEditPreferenceClick = { navigateToEditPreference(changePreferenceLauncher) },
        onBakeryLikeClick = { bakeryId, isLike ->
            viewModel.intent(
                HomeIntent.ClickBakeryLike(
                    bakeryId = bakeryId,
                    isLike = isLike
                )
            )
        }
    )
}