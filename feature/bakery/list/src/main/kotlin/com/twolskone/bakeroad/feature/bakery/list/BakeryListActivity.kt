package com.twolskone.bakeroad.feature.bakery.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.twolskone.bakeroad.core.analytics.AnalyticsHelper
import com.twolskone.bakeroad.core.analytics.LocalAnalyticsHelper
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.core.navigator.BakeryDetailNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class BakeryListActivity : ComponentActivity() {

    @Inject
    lateinit var bakeryDetailNavigator: BakeryDetailNavigator

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper               // Firebase Analytics Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SystemBarColorTheme(lightTheme = true)
            BakeRoadTheme {
                CompositionLocalProvider(
                    LocalAnalyticsHelper provides analyticsHelper
                ) {
                    BakeryListRoute(
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
                        finish = { finish() }
                    )
                }
            }
        }
    }
}