package com.twolskone.bakeroad.feature.bakery.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class BakeryListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BakeRoadTheme {
                SystemBarColorTheme(lightTheme = true)
                BakeryListRoute()
            }
        }
    }
}