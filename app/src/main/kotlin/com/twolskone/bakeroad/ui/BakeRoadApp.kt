package com.twolskone.bakeroad.ui

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.twolskone.bakeroad.MainViewModel
import com.twolskone.bakeroad.R
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.core.common.android.extension.isRouteInHierarchy
import com.twolskone.bakeroad.core.designsystem.component.navigation.BakeRoadNavigationBar
import com.twolskone.bakeroad.core.designsystem.component.navigation.BakeRoadNavigationBarItem
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.eventbus.MainEventBus
import com.twolskone.bakeroad.core.model.Badge
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.core.ui.popup.BadgeAchievedBottomSheet
import com.twolskone.bakeroad.feature.home.navigation.navigateToHome
import com.twolskone.bakeroad.feature.mybakery.navigation.navigateToMyBakery
import com.twolskone.bakeroad.feature.mypage.navigation.navigateToMyPage
import com.twolskone.bakeroad.feature.search.navigation.navigateToSearch
import com.twolskone.bakeroad.mvi.MainSideEffect
import com.twolskone.bakeroad.navigation.BakeRoadDestination
import com.twolskone.bakeroad.navigation.BakeRoadNavHost
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

private const val BackInterval = 1500L

@Composable
internal fun BakeRoadApp(
    viewModel: MainViewModel,
    mainEventBus: MainEventBus,
    navController: NavHostController,
    navigateToBakeryList: (areaCodes: String, BakeryType, launcher: ActivityResultLauncher<Intent>) -> Unit,
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int, launcher: ActivityResultLauncher<Intent>) -> Unit,
    navigateToEditPreference: (ActivityResultLauncher<Intent>) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToReport: () -> Unit,
    navigateToBadgeList: () -> Unit,
    navigateToMyReviews: () -> Unit,
    openBrowser: (url: String) -> Unit,
    finish: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    var backTimeMillis by remember { mutableLongStateOf(0) }
    var achievedBadges by remember { mutableStateOf(emptyList<Badge>()) }
    val onBack: () -> Unit = {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - backTimeMillis >= BackInterval) {
            backTimeMillis = currentTimeMillis
            viewModel.showSnackbar(
                type = SnackbarType.ERROR,
                messageRes = R.string.snackbar_press_back_again_to_exit,
                duration = BackInterval
            )
        } else {
            finish()
        }
    }

    BackHandler { onBack() }

    LaunchedEffect(Unit) {
        mainEventBus.snackbarEvent.collectLatest { snackbarState ->
            viewModel.showSnackbar(
                type = snackbarState.type,
                messageRes = snackbarState.messageRes,
                message = snackbarState.message
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                MainSideEffect.NavigateToHome -> navigateToBakeRoadDestination(
                    navController = navController,
                    destination = BakeRoadDestination.Home
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.achieveBadges
            .filter { it.isNotEmpty() }
            .collectLatest {
                achievedBadges = it
            }
    }

    BaseComposable(baseViewModel = viewModel) {
        Scaffold(
            bottomBar = {
                BakeRoadNavigationBar(modifier = Modifier.fillMaxWidth()) {
                    BakeRoadDestination.entries.fastForEach { menu ->
                        BakeRoadNavigationBarItem(
                            selected = currentDestination.isRouteInHierarchy(route = menu.route),
                            icon = menu.icon,
                            selectedIcon = menu.selectedIcon,
                            label = { Text(text = stringResource(id = menu.labelId)) },
                            onClick = {
                                // Tab reselect.
                                if (currentDestination.isRouteInHierarchy(route = menu.route)) {
                                    scope.launch {
                                        when (menu) {
                                            BakeRoadDestination.Home -> mainEventBus.reselectHome()
                                            BakeRoadDestination.Search -> {}
                                            BakeRoadDestination.MyBakery -> mainEventBus.reselectMyBakery()
                                            BakeRoadDestination.MyPage -> {}
                                        }
                                    }
                                }
                                navigateToBakeRoadDestination(navController = navController, destination = menu)
                            }
                        )
                    }
                }
            },
        ) { contentPadding ->
            BakeRoadNavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
                padding = contentPadding,
                navController = navController,
                navigateToBakeryList = navigateToBakeryList,
                navigateToBakeryDetail = navigateToBakeryDetail,
                navigateToEditPreference = navigateToEditPreference,
                navigateToSettings = navigateToSettings,
                navigateToReport = navigateToReport,
                navigateToBadgeList = navigateToBadgeList,
                navigateToMyReviews = navigateToMyReviews,
                openBrowser = openBrowser,
                goBack = onBack
            )
        }

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
}

private fun navigateToBakeRoadDestination(
    navController: NavHostController,
    destination: BakeRoadDestination
) {
    val navOptions = navOptions {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
    when (destination) {
        BakeRoadDestination.Home -> navController.navigateToHome(navOptions = navOptions)
        BakeRoadDestination.Search -> navController.navigateToSearch(navOptions = navOptions)
        BakeRoadDestination.MyBakery -> navController.navigateToMyBakery(navOptions = navOptions)
        BakeRoadDestination.MyPage -> navController.navigateToMyPage(navOptions = navOptions)
    }
}