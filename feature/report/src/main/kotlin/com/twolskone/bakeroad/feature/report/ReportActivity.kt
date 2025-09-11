package com.twolskone.bakeroad.feature.report

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.twolskone.bakeroad.core.analytics.AnalyticsHelper
import com.twolskone.bakeroad.core.analytics.LocalAnalyticsHelper
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.feature.report.navigation.ReportNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class ReportActivity : ComponentActivity() {

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
                    ReportNavHost(
                        navController = rememberNavController(),
                        finish = { finish() }
                    )
                }
            }
        }
    }
}