package com.twolskone.bakeroad.feature.review.write

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.feature.review.write.navigation.WriteBakeryReviewNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class WriteBakeryReviewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BakeRoadTheme {
                SystemBarColorTheme(lightTheme = true)
                WriteBakeryReviewNavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding(),
                    navController = rememberNavController(),
                    finish = { finish() }
                )
            }
        }
    }
}