package com.twolskone.bakeroad.feature.settings

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.core.navigator.IntroNavigator
import com.twolskone.bakeroad.core.navigator.util.KEY_LOGIN
import com.twolskone.bakeroad.feature.settings.navigation.SettingsNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SettingsActivity : ComponentActivity() {

    @Inject
    lateinit var introNavigator: IntroNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SystemBarColorTheme(lightTheme = true)
            BakeRoadTheme {
                SettingsNavHost(
                    navController = rememberNavController(),
                    navigateToLogin = {
                        introNavigator.navigateFromActivity(
                            activity = this,
                            withFinish = true,
                            intentBuilder = {
                                putExtra(KEY_LOGIN, true)
                                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                        )
                    }
                )
            }
        }
    }
}