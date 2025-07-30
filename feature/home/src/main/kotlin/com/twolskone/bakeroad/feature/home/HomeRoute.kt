package com.twolskone.bakeroad.feature.home

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.feature.home.mvi.HomeIntent
import timber.log.Timber

@Composable
internal fun HomeRoute(
    padding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToBakeryList: (areaCodes: String, BakeryType) -> Unit,
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int) -> Unit,
    navigateToEditPreference: (ActivityResultLauncher<Intent>) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val changePreferenceLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Timber.i("xxx changePreferenceLauncher :: Completed change preference")
            viewModel.showSnackbar(
                type = SnackbarType.SUCCESS,
                message = context.getString(R.string.feature_home_snackbar_complete_edit_preference)
            )
        } else {
            Timber.i("xxx changePreferenceLauncher :: Canceled change preference")
        }
    }

    BaseComposable(baseViewModel = viewModel) {
        HomeScreen(
            padding = padding,
            state = state,
            onAreaSelect = { selected, code -> viewModel.intent(HomeIntent.SelectArea(selected = selected, areaCode = code)) },
            onTourCategorySelect = { selected, category -> viewModel.intent(HomeIntent.SelectTourAreaCategory(selected = selected, category = category)) },
            onSeeAllBakeriesClick = { bakeryType ->
                navigateToBakeryList(
                    state.selectedAreaCodes.joinToString(separator = ","),
                    bakeryType
                )
            },
            onBakeryClick = { bakery -> navigateToBakeryDetail(bakery.id, bakery.areaCode) },
            onEditPreferenceClick = { navigateToEditPreference(changePreferenceLauncher) }
        )
    }
}