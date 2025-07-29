package com.twolskone.bakeroad.feature.onboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.feature.onboard.navigation.OnBoardingNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class OnboardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SystemBarColorTheme(lightTheme = true)
            OnBoardingNavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding(),
                navController = rememberNavController(),
                finish = { finish() },
                setResult = { code, finish ->
                    setResult(code)
                    if (finish) finish()
                }
            )
        }
    }
}