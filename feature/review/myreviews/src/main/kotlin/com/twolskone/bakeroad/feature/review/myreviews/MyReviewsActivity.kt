package com.twolskone.bakeroad.feature.review.myreviews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyReviewsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SystemBarColorTheme(lightTheme = true)
            MyReviewRoute()
        }
    }
}