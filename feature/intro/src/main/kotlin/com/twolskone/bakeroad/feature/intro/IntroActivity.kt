package com.twolskone.bakeroad.feature.intro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.White
import com.twolskone.bakeroad.core.navigator.OnboardingNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class IntroActivity : ComponentActivity() {

    @Inject
    lateinit var onboardingNavigator: OnboardingNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        setSystemBarColorTheme(lightTheme = true)
        super.onCreate(savedInstanceState)

        setContent {
            BakeRoadTheme {
                IntroRoute(
                    navigateToHome = {},
                    navigateToOnboarding = { onboardingNavigator.navigateFromActivity(activity = this, withFinish = true) }
                )
            }
        }

        // Disable default splash screen exit animation.
        splashScreen.setOnExitAnimationListener {
            it.remove()
            setSystemBarColorTheme(lightTheme = true)
        }
    }

    private fun setSystemBarColorTheme(lightTheme: Boolean) {
        val systemBarColor = White.toArgb()

        if (lightTheme) {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(scrim = systemBarColor, darkScrim = systemBarColor),
                navigationBarStyle = SystemBarStyle.light(scrim = systemBarColor, darkScrim = systemBarColor),
            )
        } else {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(scrim = systemBarColor),
                navigationBarStyle = SystemBarStyle.dark(scrim = systemBarColor)
            )
        }
    }
}