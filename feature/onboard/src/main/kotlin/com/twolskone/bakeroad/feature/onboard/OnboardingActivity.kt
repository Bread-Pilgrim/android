package com.twolskone.bakeroad.feature.onboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.twolskone.bakeroad.core.analytics.AnalyticsHelper
import com.twolskone.bakeroad.core.analytics.LocalAnalyticsHelper
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.core.navigator.MainNavigator
import com.twolskone.bakeroad.core.navigator.util.KEY_BADGE_ACHIEVED
import com.twolskone.bakeroad.feature.onboard.navigation.OnBoardingNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class OnboardingActivity : ComponentActivity() {

    @Inject
    lateinit var mainNavigator: MainNavigator

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper   // Firebase Analytics Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SystemBarColorTheme(lightTheme = true)
            BakeRoadTheme {
                CompositionLocalProvider(
                    LocalAnalyticsHelper provides analyticsHelper
                ) {
                    OnBoardingNavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .imePadding(),
                        navController = rememberNavController(),
                        finish = { finish() },
                        setResult = { code, finish ->
                            setResult(code)
                            if (finish) finish()
                        },
                        navigateToMain = { achievedBadges ->
                            mainNavigator.navigateFromActivity(
                                activity = this,
                                withFinish = true,
                                intentBuilder = {
                                    if (achievedBadges.isNotEmpty()) {
                                        putExtra(KEY_BADGE_ACHIEVED, ArrayList(achievedBadges))
                                    } else {
                                        this
                                    }
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}