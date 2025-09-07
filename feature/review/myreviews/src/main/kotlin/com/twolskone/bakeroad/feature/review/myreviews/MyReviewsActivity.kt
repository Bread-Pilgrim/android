package com.twolskone.bakeroad.feature.review.myreviews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.core.navigator.BakeryDetailNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyReviewsActivity : ComponentActivity() {

    @Inject
    lateinit var bakeryDetailNavigator: BakeryDetailNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SystemBarColorTheme(lightTheme = true)
            BakeRoadTheme {
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