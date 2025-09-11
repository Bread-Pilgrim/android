package com.twolskone.bakeroad.feature.review.myreviews

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
class MyReviewsActivity : ComponentActivity() {

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
                    MyReviewRoute(
                        navigateToBakeryDetail = { bakeryId, areaCode ->
                            bakeryDetailNavigator.navigateFromActivity(
                                activity = this,
                                withFinish = false,
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