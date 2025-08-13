package com.twolskone.bakeroad.feature.report

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.feature.report.navigation.ReportNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class ReportActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SystemBarColorTheme(lightTheme = true)
            BakeRoadTheme {
                ReportNavHost(
                    navController = rememberNavController()
                )
            }
        }
    }
}