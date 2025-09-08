package com.twolskone.bakeroad.feature.home

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.model.Badge
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.core.ui.popup.BadgeAchievedBottomSheet
import com.twolskone.bakeroad.feature.home.mvi.HomeIntent
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
internal fun HomeRoute(
    padding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToBakeryList: (areaCodes: String, BakeryType, launcher: ActivityResultLauncher<Intent>) -> Unit,
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int, launcher: ActivityResultLauncher<Intent>) -> Unit,
    navigateToEditPreference: (ActivityResultLauncher<Intent>) -> Unit,
    navigateToBadgeList: () -> Unit,
    openBrowser: (url: String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val verticalScrollState = rememberLazyListState()
    var achievedBadges by remember { mutableStateOf(emptyList<Badge>()) }
    val changePreferenceLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Timber.i("xxx bakeryDetailLauncher :: resultCode ${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.intent(
                HomeIntent.RefreshBakeries(
                    completeSnackbarState = SnackbarState(
                        type = SnackbarType.SUCCESS,
                        messageRes = R.string.feature_home_snackbar_complete_edit_preference,
                    )
                )
            )
        }
    }
    val bakeryListLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Timber.i("xxx bakeryDetailLauncher :: resultCode ${result.resultCode}")
    }
    val bakeryDetailLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Timber.i("xxx bakeryDetailLauncher :: resultCode ${result.resultCode}")
    }

    LaunchedEffect(Unit) {
        viewModel.mainEventBus.homeRefreshState.collectLatest { refresh ->
            if (refresh) {
                Timber.i("xxx collect homeRefreshState")
                viewModel.mainEventBus.setHomeRefreshState(value = false)
                viewModel.intent(HomeIntent.RefreshBakeries())
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.mainEventBus.homeReselectEvent.collect {
            Timber.i("xxx collect homeReselectEvent")
            verticalScrollState.animateScrollToItem(0)
        }
    }

    HomeScreen(
        padding = padding,
        verticalScrollState = verticalScrollState,
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
        onAreaEventSeeDetailsClick = { link -> openBrowser(link) },
        onAreaEventSheetDismiss = { isTodayDismissed -> viewModel.intent(HomeIntent.DismissAreaEventSheet(isTodayDismissed = isTodayDismissed)) }
    )

    if (achievedBadges.isNotEmpty()) {
        BadgeAchievedBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            badgeList = achievedBadges.toImmutableList(),
            onDismissRequest = { achievedBadges = emptyList() },
            onSeeBadgeClick = {
                achievedBadges = emptyList()
                navigateToBadgeList()
            }
        )
    }
}