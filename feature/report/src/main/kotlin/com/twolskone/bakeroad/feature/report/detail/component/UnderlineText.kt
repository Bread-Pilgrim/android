package com.twolskone.bakeroad.feature.report.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

private val OffsetY = 2.dp
private val UnderlineHeight = 6.dp

@Composable
fun UnderlineText(
    text: String,
    textStyle: TextStyle,
    underlineColor: Color = BakeRoadTheme.colorScheme.Primary50
) {
    val density = LocalDensity.current
    val offset = IntOffset(x = 0, y = with(density) { OffsetY.toPx().toInt() })
    val underlineHeight = with(density) { UnderlineHeight.toPx() }

    Box(modifier = Modifier.padding(bottom = OffsetY)) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset { offset }
                .graphicsLayer { clip = true }
                .drawBehind {
                    drawRect(
                        color = underlineColor,
                        topLeft = Offset(0f, size.height - underlineHeight),
                        size = Size(size.width, underlineHeight)
                    )
                }
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            style = textStyle
        )
    }
}

@Composable
fun UnderlineText(
    modifier: Modifier = Modifier,
    annotatedText: AnnotatedString,
    textStyle: TextStyle,
    underlineColor: Color = BakeRoadTheme.colorScheme.Primary50
) {
    val density = LocalDensity.current
    val offset = IntOffset(x = 0, y = with(density) { OffsetY.toPx().toInt() })
    val underlineHeight = with(density) { UnderlineHeight.toPx() }

    Box(modifier = modifier.padding(bottom = OffsetY)) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset { offset }
                .graphicsLayer { clip = true }
                .drawBehind {
                    drawRect(
                        color = underlineColor,
                        topLeft = Offset(0f, size.height - underlineHeight),
                        size = Size(size.width, underlineHeight)
                    )
                }
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = annotatedText,
            style = textStyle
        )
    }
}

@Preview
@Composable
private fun UnderlineTextPreview() {
    BakeRoadTheme {
        Box(modifier = Modifier.background(color = BakeRoadTheme.colorScheme.White)) {
            UnderlineText(
                text = "전달 대비 1.2만원 더 썼어요!",
                textStyle = BakeRoadTheme.typography.bodyXsmallMedium
            )
        }
    }
}