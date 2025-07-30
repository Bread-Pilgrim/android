package com.twolskone.bakeroad.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

private const val LikeClickInterval = 400L

/**
 * 좋아요 아이콘
 */
@Composable
fun LikeIcon(
    modifier: Modifier = Modifier,
    size: Dp,
    padding: Dp,
    colors: LikeIconColors = LikeIconDefaults.colors(),
    isLike: Boolean,
    onClick: (Boolean) -> Unit
) {
    Icon(
        modifier = Modifier
            .then(modifier)
            .clip(CircleShape)
            .background(color = colors.containerColor)
            .singleClickable(clickInterval = LikeClickInterval, onClick = { onClick(!isLike) })
            .padding(padding)
            .size(size),
        imageVector = ImageVector.vectorResource(
            id = if (isLike) {
                R.drawable.core_ui_ic_heart_fill
            } else {
                R.drawable.core_ui_ic_heart_stroke
            }
        ),
        contentDescription = "Bookmark",
        tint = if (isLike) {
            colors.likeIconContentColor
        } else {
            colors.unlikeIconContentColor
        }
    )
}

object LikeIconDefaults {

    @Composable
    fun colors(
        containerColor: Color = Color.Transparent,
        likeIconContentColor: Color = BakeRoadTheme.colorScheme.Error500,
        unlikeIconContentColor: Color = BakeRoadTheme.colorScheme.White
    ): LikeIconColors = LikeIconColors(
        containerColor = containerColor,
        likeIconContentColor = likeIconContentColor,
        unlikeIconContentColor = unlikeIconContentColor
    )
}

@Immutable
data class LikeIconColors(
    val containerColor: Color,
    val likeIconContentColor: Color,
    val unlikeIconContentColor: Color
)