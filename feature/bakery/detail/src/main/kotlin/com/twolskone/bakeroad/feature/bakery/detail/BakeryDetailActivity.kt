package com.twolskone.bakeroad.feature.bakery.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.core.navigator.WriteReviewNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class BakeryDetailActivity : ComponentActivity() {

    @Inject
    lateinit var writeReviewNavigator: WriteReviewNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BakeRoadTheme {
                SystemBarColorTheme(lightTheme = true)
                BakeryDetailRoute(
                    navigateToWriteReview = { bakeryId ->
                        writeReviewNavigator.navigateFromActivity(
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