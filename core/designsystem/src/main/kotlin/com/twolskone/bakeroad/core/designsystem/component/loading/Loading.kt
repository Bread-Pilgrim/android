package com.twolskone.bakeroad.core.designsystem.component.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

@Composable
fun BakeRoadLoading(
    modifier: Modifier = Modifier,
    type: LoadingType,
    colors: LoadingColors = BakeRoadLoadingDefaults.colors(type),
    size: Dp = BakeRoadLoadingDefaults.size(type),
    trackWidth: Dp = BakeRoadLoadingDefaults.trackWidth(type)
) {
    val rotation = rememberInfiniteTransition()
        .animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 600, easing = LinearEasing)
            )
        )

    Box(
        modifier = modifier
            .size(size)
            .background(color = colors.backgroundColor, shape = CircleShape)
            .padding(trackWidth / 2)
            .rotate(rotation.value)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val sweepAngle = 68.82f
            val stroke = Stroke(width = trackWidth.toPx(), cap = StrokeCap.Round)

            // Track.
            drawArc(
                color = colors.trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = stroke
            )

            // Stroke.
            drawArc(
                color = colors.indicatorColor,
                startAngle = 0f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = stroke
            )
        }
    }
}

@Composable
fun BakeRoadLoadingScreen(
    modifier: Modifier = Modifier,
    touchBlocking: Boolean = true,
    type: LoadingType,
    colors: LoadingColors = BakeRoadLoadingDefaults.colors(type),
    size: Dp = BakeRoadLoadingDefaults.size(type),
    trackWidth: Dp = BakeRoadLoadingDefaults.trackWidth(type)
) {
    Box(
        modifier = modifier
            .then(
                if (touchBlocking) {
                    Modifier.pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                awaitPointerEvent()
                            }
                        }
                    }
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        BakeRoadLoading(
            type = type,
            colors = colors,
            size = size,
            trackWidth = trackWidth
        )
    }
}

object BakeRoadLoadingDefaults {

    @Composable
    fun colors(type: LoadingType) = when (type) {
        LoadingType.Default -> LoadingColors(
            indicatorColor = BakeRoadTheme.colorScheme.Primary500,
            trackColor = BakeRoadTheme.colorScheme.Primary100,
            backgroundColor = Color.Transparent
        )

        LoadingType.Button -> LoadingColors(
            indicatorColor = BakeRoadTheme.colorScheme.White,
            trackColor = BakeRoadTheme.colorScheme.Primary200,
            backgroundColor = Color.Transparent
        )
    }

    @Composable
    fun size(type: LoadingType) = when (type) {
        LoadingType.Default -> 40.dp
        LoadingType.Button -> 28.dp
    }

    @Composable
    fun trackWidth(type: LoadingType) = when (type) {
        LoadingType.Default -> 6.dp
        LoadingType.Button -> 4.dp
    }
}

@Immutable
data class LoadingColors(
    val indicatorColor: Color,
    val trackColor: Color,
    val backgroundColor: Color
)

enum class LoadingType {
    Default, Button
}

@Preview(showBackground = true)
@Composable
private fun BakeRoadLoadingPreview() {
    BakeRoadTheme {
        BakeRoadLoading(type = LoadingType.Default)
    }
}
