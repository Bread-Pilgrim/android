package com.twolskone.bakeroad.feature.intro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class IntroActivity : ComponentActivity() {

    private val viewModel: IntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            BakeRoadTheme {
                IntroRoute()
            }
        }

        splashScreen.setKeepOnScreenCondition { viewModel.state.value.shouldKeepSplashScreen }
    }
}