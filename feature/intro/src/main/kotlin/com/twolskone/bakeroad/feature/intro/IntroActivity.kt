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
import com.twolskone.bakeroad.core.navigator.MainNavigator
import com.twolskone.bakeroad.core.navigator.OnboardingNavigator
import com.twolskone.bakeroad.core.navigator.util.KEY_LOGIN
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class IntroActivity : ComponentActivity() {

    @Inject
    lateinit var onboardingNavigator: OnboardingNavigator

    @Inject
    lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = if (intent.getBooleanExtra(KEY_LOGIN, false)) {
            setTheme(com.twolskone.bakeroad.core.designsystem.R.style.CoreDesignSystem_BakeRoad_Theme)
            null
        } else {
            installSplashScreen()
        }
        setSystemBarColorTheme(lightTheme = true)
        super.onCreate(savedInstanceState)

        setContent {
            BakeRoadTheme {
                IntroRoute(
                    navigateToHome = { mainNavigator.navigateFromActivity(activity = this, withFinish = true) },
                    navigateToOnboarding = { onboardingNavigator.navigateFromActivity(activity = this, withFinish = true) }
                )
            }
        }

        // Disable default splash screen exit animation.
        splashScreen?.setOnExitAnimationListener {
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