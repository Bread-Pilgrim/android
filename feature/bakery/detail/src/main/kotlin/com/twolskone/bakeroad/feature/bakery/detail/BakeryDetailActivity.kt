package com.twolskone.bakeroad.feature.bakery.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.core.navigator.WriteBakeryReviewNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class BakeryDetailActivity : ComponentActivity() {

    @Inject
    lateinit var writeBakeryReviewNavigator: WriteBakeryReviewNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BakeRoadTheme {
                SystemBarColorTheme(lightTheme = true)
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
                    }
                )
            }
        }
    }
}