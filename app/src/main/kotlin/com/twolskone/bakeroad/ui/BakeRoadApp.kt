package com.twolskone.bakeroad.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.twolskone.bakeroad.core.common.android.base.extension.isRouteInHierarchy
import com.twolskone.bakeroad.core.designsystem.component.navigation.BakeRoadNavigationBar
import com.twolskone.bakeroad.core.designsystem.component.navigation.BakeRoadNavigationBarItem
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.navigation.BakeRoadMenu
import com.twolskone.bakeroad.navigation.BakeRoadNavHost

@Composable
internal fun BakeRoadApp(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigateToBakeryList: (BakeryType) -> Unit
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
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
        }
    ) { contentPadding ->
        BakeRoadNavHost(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
                .systemBarsPadding(),
            navController = navController,
            navigateToBakeryList = navigateToBakeryList
        )
    }
}