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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

private val LoadingSize = 28.dp
private val LoadingStrokeWidth = 4.dp

@Composable
fun BakeRoadLoading(
    modifier: Modifier = Modifier,
    indicatorColor: Color = BakeRoadTheme.colorScheme.White,
    trackColor: Color = BakeRoadTheme.colorScheme.Primary200,
    backgroundColor: Color = Color.Transparent
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
            .size(LoadingSize)
            .background(color = backgroundColor, shape = CircleShape)
            .padding(LoadingStrokeWidth / 2)
            .rotate(rotation.value)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val sweepAngle = 68.82f
            val stroke = Stroke(width = LoadingStrokeWidth.toPx(), cap = StrokeCap.Round)

            // Track.
            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = stroke
            )

            // Stroke.
            drawArc(
                color = indicatorColor,
                startAngle = 0f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = stroke
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BakeRoadLoadingPreview() {
    BakeRoadTheme {
        BakeRoadLoading(modifier = Modifier.size(20.dp))
    }
}
