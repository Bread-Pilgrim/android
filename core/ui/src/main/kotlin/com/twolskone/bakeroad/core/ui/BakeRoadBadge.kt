package com.twolskone.bakeroad.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.Badge

private val BadgeSize = 90.dp
private val BadgeImageSize = 72.dp
private val BadgeShape = RoundedCornerShape(20.dp)

@Composable
fun BakeRoadBadge(
    modifier: Modifier = Modifier,
    size: Dp = BadgeSize,
    imageSize: Dp = BadgeImageSize,
    badge: Badge
) {
    Box(
        modifier = modifier
            .clip(BadgeShape)
            .graphicsLayer { alpha = if (badge.isEarned) 1.0f else 0.3f }
            .background(color = BakeRoadTheme.colorScheme.Gray50, BadgeShape)
            .size(size),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier.size(imageSize),
            model = badge.imageUrl,
            contentDescription = "Badge",
        )
    }
}

@Preview
@Composable
private fun BadgePreview() {
    BakeRoadTheme {
        BakeRoadBadge(badge = Badge.ofEmpty())
    }
}