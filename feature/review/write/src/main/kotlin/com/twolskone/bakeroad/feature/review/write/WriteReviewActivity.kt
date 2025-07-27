package com.twolskone.bakeroad.feature.review.write

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class WriteReviewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BakeRoadTheme {
                SystemBarColorTheme(lightTheme = true)
            }
        }
    }
}