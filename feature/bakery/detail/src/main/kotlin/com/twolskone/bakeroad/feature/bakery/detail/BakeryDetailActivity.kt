package com.twolskone.bakeroad.feature.bakery.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class BakeryDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BakeRoadTheme {
                SystemBarColorTheme(lightTheme = true)
                BakeryDetailScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}