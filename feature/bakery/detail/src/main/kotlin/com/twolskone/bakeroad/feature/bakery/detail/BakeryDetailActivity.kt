package com.twolskone.bakeroad.feature.bakery.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.twolskone.bakeroad.core.analytics.AnalyticsHelper
import com.twolskone.bakeroad.core.analytics.LocalAnalyticsHelper
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.core.navigator.BadgeListNavigator
import com.twolskone.bakeroad.core.navigator.WriteBakeryReviewNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class BakeryDetailActivity : ComponentActivity() {

    @Inject
    lateinit var writeBakeryReviewNavigator: WriteBakeryReviewNavigator

    @Inject
    lateinit var badgeListNavigator: BadgeListNavigator

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper                       // Firebase Analytics Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SystemBarColorTheme(lightTheme = true)
            BakeRoadTheme {
                CompositionLocalProvider(
                    LocalAnalyticsHelper provides analyticsHelper
                ) {
                    BakeryDetailRoute(
                        navigateToWriteBakeryReview = { bakeryId, launcher ->
                            writeBakeryReviewNavigator.navigateFromLauncher(
                                activity = this,
                                intentBuilder = { putExtra("bakeryId", bakeryId) },
                                launcher = launcher
                            )
                        },
                        setResult = { code, intent, finish ->
                            if (intent != null) {
                                setResult(code, intent)
                            } else {
                                setResult(code)
                            }
                            if (finish) finish()
                        },
                        navigateToBadgeList = {
                            badgeListNavigator.navigateFromActivity(
                                activity = this,
                                withFinish = false
                            )
                        }
                    )
                }
            }
        }
    }
}