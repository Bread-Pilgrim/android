package com.twolskone.bakeroad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.core.navigator.BakeryDetailNavigator
import com.twolskone.bakeroad.core.navigator.BakeryListNavigator
import com.twolskone.bakeroad.core.navigator.OnboardingNavigator
import com.twolskone.bakeroad.ui.BakeRoadApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var bakeryListNavigator: BakeryListNavigator

    @Inject
    lateinit var bakeryDetailNavigator: BakeryDetailNavigator

    @Inject
    lateinit var onboardingNavigator: OnboardingNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SystemBarColorTheme(lightTheme = true)
            BakeRoadTheme {
                BakeRoadApp(
                    navController = rememberNavController(),
                    navigateToBakeryList = { areaCodes, bakeryType, launcher ->
                        bakeryListNavigator.navigateFromLauncher(
                            activity = this,
                            launcher = launcher,
                            intentBuilder = {
                                putExtra("areaCodes", areaCodes)
                                putExtra("bakeryType", bakeryType)
                            }
                        )
                    },
                    navigateToBakeryDetail = { bakeryId, areaCode, launcher ->
                        bakeryDetailNavigator.navigateFromLauncher(
                            activity = this,
                            launcher = launcher,
                            intentBuilder = {
                                putExtra("bakeryId", bakeryId)
                                putExtra("areaCode", areaCode)
                            }
                        )
                    },
                    navigateToEditPreference = { launcher ->
                        onboardingNavigator.navigateFromLauncher(
                            activity = this,
                            intentBuilder = { putExtra("isEditPreference", true) },
                            launcher = launcher
                        )
                    }
                )
            }
        }
    }
}