package com.twolskone.bakeroad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.core.navigator.BakeryDetailNavigator
import com.twolskone.bakeroad.core.navigator.BakeryListNavigator
import com.twolskone.bakeroad.ui.BakeRoadApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var bakeryListNavigator: BakeryListNavigator

    @Inject
    lateinit var bakeryDetailNavigator: BakeryDetailNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SystemBarColorTheme(lightTheme = true)
            BakeRoadTheme {
                BakeRoadApp(
                    navController = rememberNavController(),
                    navigateToBakeryList = { areaCode, bakeryType ->
                        bakeryListNavigator.navigateFromActivity(
                            activity = this,
                            withFinish = false,
                            intentBuilder = {
                                putExtra("areaCode", areaCode)
                                putExtra("bakeryType", bakeryType)
                            }
                        )
                    },
                    navigateToBakeryDetail = { bakeryId ->
                        bakeryDetailNavigator.navigateFromActivity(
                            activity = this,
                            withFinish = false,
                            intentBuilder = { putExtra("bakeryId", bakeryId) }
                        )
                    }
                )
            }
        }
    }
}