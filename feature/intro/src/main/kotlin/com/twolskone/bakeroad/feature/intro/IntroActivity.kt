package com.twolskone.bakeroad.feature.intro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.core.navigator.OnboardingNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class IntroActivity : ComponentActivity() {

    @Inject
    lateinit var onboardingNavigator: OnboardingNavigator

    private val viewModel: IntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            SystemBarColorTheme(lightTheme = true)
            BakeRoadTheme {
                IntroRoute(
                    navigateToHome = {},
                    navigateToOnboarding = { onboardingNavigator.navigateFromActivity(activity = this, withFinish = true) }
                )
            }
        }

        splashScreen.setKeepOnScreenCondition { viewModel.state.value.shouldKeepSplashScreen }
    }
}