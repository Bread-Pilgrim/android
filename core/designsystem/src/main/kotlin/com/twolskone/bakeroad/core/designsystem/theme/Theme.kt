package com.twolskone.bakeroad.core.designsystem.theme

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.toArgb

/**
 * This design system is reconstructed to fit the design guide.
 * (ColorScheme, Typography)
 */
object BakeRoadTheme {
    val colorScheme: BakeRoadColorScheme
        @ReadOnlyComposable @Composable
        get() = LocalBakeRoadColorScheme.current
    val typography: BakeRoadTypography
        @ReadOnlyComposable @Composable
        get() = LocalBakeRoadTypography.current
}

val LightDefaultColorScheme = lightColorScheme(
    primary = Primary500,
    onPrimary = White,
    primaryContainer = Primary500,
    onPrimaryContainer = White,
    secondary = Secondary500,
    onSecondary = White,
    secondaryContainer = Secondary500,
    onSecondaryContainer = White,
    error = Error500,
    onError = White,
    errorContainer = Error500,
    onErrorContainer = White,
    background = White,
    onBackground = Gray950
)

@Composable
fun BakeRoadTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalBakeRoadTypography provides bakeRoadTypography,
        LocalBakeRoadColorScheme provides bakeRoadLightColorScheme
    ) {
        MaterialTheme(
            colorScheme = LightDefaultColorScheme,  // Use BakeRoadTheme.colorScheme
            typography = Typography(),              // Use BakeRoadTheme.typography
            content = content
        )
    }
}

@Composable
fun ComponentActivity.SystemBarColorTheme(lightTheme: Boolean) {
    val systemBarColor = BakeRoadTheme.colorScheme.White.toArgb()

    LaunchedEffect(lightTheme) {
        if (lightTheme) {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(scrim = systemBarColor, darkScrim = systemBarColor),
                navigationBarStyle = SystemBarStyle.light(scrim = systemBarColor, darkScrim = systemBarColor),
            )
        } else {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(scrim = systemBarColor),
                navigationBarStyle = SystemBarStyle.dark(scrim = systemBarColor)
            )
        }
    }
}