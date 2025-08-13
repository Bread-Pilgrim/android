package com.twolskone.bakeroad.ui

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.twolskone.bakeroad.core.common.android.extension.isRouteInHierarchy
import com.twolskone.bakeroad.core.designsystem.component.navigation.BakeRoadNavigationBar
import com.twolskone.bakeroad.core.designsystem.component.navigation.BakeRoadNavigationBarItem
import com.twolskone.bakeroad.core.designsystem.component.snackbar.BakeRoadSnackbarHost
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.feature.home.navigation.navigateToHome
import com.twolskone.bakeroad.feature.mybakery.navigation.navigateToMyBakery
import com.twolskone.bakeroad.feature.mypage.navigation.navigateToMyPage
import com.twolskone.bakeroad.feature.search.navigation.navigateToSearch
import com.twolskone.bakeroad.navigation.BakeRoadDestination
import com.twolskone.bakeroad.navigation.BakeRoadNavHost

@Composable
internal fun BakeRoadApp(
    navController: NavHostController,
    navigateToBakeryList: (areaCodes: String, BakeryType, launcher: ActivityResultLauncher<Intent>) -> Unit,
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int, launcher: ActivityResultLauncher<Intent>) -> Unit,
    navigateToEditPreference: (ActivityResultLauncher<Intent>) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToReport: () -> Unit
) {
    val context = LocalContext.current
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarTrigger: Pair<SnackbarState, Long>? by remember { mutableStateOf(null) }

    LaunchedEffect(snackbarTrigger) {
        snackbarTrigger?.let { (state, _) ->
            snackbarHostState.showSnackbar(
                message = state.messageRes?.let { context.getString(it) } ?: state.message,
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    Scaffold(
        bottomBar = {
            BakeRoadNavigationBar(modifier = Modifier.fillMaxWidth()) {
                BakeRoadDestination.entries.fastForEach { menu ->
                    BakeRoadNavigationBarItem(
                        selected = currentDestination.isRouteInHierarchy(route = menu.route),
                        icon = menu.icon,
                        selectedIcon = menu.selectedIcon,
                        label = { Text(text = stringResource(id = menu.labelId)) },
                        onClick = { navigateToBakeRoadDestination(navController = navController, destination = menu) }
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
            showSnackbar = { state -> snackbarTrigger = state to System.currentTimeMillis() }
        )
    }

    snackbarTrigger?.let { (state, _) ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.navigationBars),
            contentAlignment = Alignment.BottomCenter
        ) {
            BakeRoadSnackbarHost(
                snackbarHostState = snackbarHostState,
                type = state.type,
                durationMills = state.duration
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