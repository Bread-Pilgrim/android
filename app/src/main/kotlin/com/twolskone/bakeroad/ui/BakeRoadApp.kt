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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.twolskone.bakeroad.core.common.android.extension.isRouteInHierarchy
import com.twolskone.bakeroad.core.designsystem.component.navigation.BakeRoadNavigationBar
import com.twolskone.bakeroad.core.designsystem.component.navigation.BakeRoadNavigationBarItem
import com.twolskone.bakeroad.core.designsystem.component.snackbar.BakeRoadSnackbarHost
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.navigation.BakeRoadMenu
import com.twolskone.bakeroad.navigation.BakeRoadNavHost

@Composable
internal fun BakeRoadApp(
    navController: NavHostController,
    navigateToBakeryList: (areaCodes: String, BakeryType, launcher: ActivityResultLauncher<Intent>) -> Unit,
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int, launcher: ActivityResultLauncher<Intent>) -> Unit,
    navigateToEditPreference: (ActivityResultLauncher<Intent>) -> Unit
) {
    val context = LocalContext.current
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarState: SnackbarState? by remember { mutableStateOf(null) }

    LaunchedEffect(snackbarState) {
        snackbarState?.let { state ->
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
                BakeRoadMenu.entries.fastForEach { menu ->
                    BakeRoadNavigationBarItem(
                        selected = currentDestination.isRouteInHierarchy(route = menu.route),
                        icon = menu.icon,
                        selectedIcon = menu.selectedIcon,
                        label = { Text(text = stringResource(id = menu.labelId)) },
                        onClick = {}
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
            showSnackbar = { state ->
                snackbarState = state
            }
        )
    }

    snackbarState?.let { state ->
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