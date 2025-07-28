package com.twolskone.bakeroad.core.designsystem.component.ratingbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastRoundToInt
import com.twolskone.bakeroad.core.designsystem.R
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import kotlin.math.floor
import timber.log.Timber

@Composable
fun BakeRoadRatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    starCount: Int = 5,
    starSize: Dp = 32.dp,
    starSpacing: Dp = 4.dp,
    onRatingChange: (Float) -> Unit
) {
//    var containerWidth by remember { mutableFloatStateOf(0f) }
    val starStates by remember(rating) {
        derivedStateOf {
            List(starCount) { index ->
                val currentCount = index + 1
                // rating : 2.5
                // currentCount: 3
                when {
                    currentCount <= rating -> RatingStarType.FULL
                    (currentCount - 0.5f) == rating -> RatingStarType.HALF
                    else -> RatingStarType.EMPTY
                }
            }
        }
    }

    Row(
        modifier = modifier
//            .onSizeChanged { containerWidth = it.width.toFloat() }
            .pointerInput(Unit) {
//                detectDragGestures { change, _ ->
//                    val x = change.position.x.coerceIn(0f, containerWidth)
//                    val starWidth = containerWidth / starCount
//                    val preciseRating = (x / starWidth).toBigDecimal().setScale(1, RoundingMode.HALF_UP).toFloat()
//                    val halfStepRating = (preciseRating * 2).toInt() / 2f + 0.5f
//                    if (halfStepRating != rating) {
//                        onRatingChange(halfStepRating.coerceIn(0.5f, starCount.toFloat()))
//                    }
//                }
                detectTapGestures { offset ->
                    val ratingOffset = offset.x / (starSize + starSpacing).toPx()
                    val ratingIndex = floor(ratingOffset).toInt()
                    val ratingFraction = when {
                        ratingOffset < ratingOffset.fastRoundToInt() -> 1f
                        ratingIndex == ratingOffset.fastRoundToInt() -> 0.5f
                        else -> 0f

                    }
                    Timber.i("xxxx ratingOffset: $ratingOffset")
                    Timber.i("xxxx ratingIndex: $ratingIndex")

                    onRatingChange(ratingIndex + ratingFraction)
                }
            },
        horizontalArrangement = Arrangement.spacedBy(space = starSpacing, alignment = Alignment.CenterHorizontally),
    ) {
        starStates.fastForEach { starType ->
            RatingStar(type = starType, size = starSize)
        }
    }
}

@Composable
private fun RatingStar(type: RatingStarType, size: Dp) {
    val icon = when (type) {
        RatingStarType.FULL -> ImageVector.vectorResource(id = R.drawable.core_designsystem_ic_star_fill)
        RatingStarType.HALF -> ImageVector.vectorResource(id = R.drawable.core_designsystem_ic_star_half_fill)
        RatingStarType.EMPTY -> ImageVector.vectorResource(id = R.drawable.core_designsystem_ic_star_stroke)
    }

    Image(
        modifier = Modifier.size(size),
        imageVector = icon,
        contentDescription = "RatingStar"
    )
}

enum class RatingStarType {
    FULL, HALF, EMPTY
}

@Preview
@Composable
private fun BakeRoadRatingBarPreview() {
    BakeRoadTheme {
        var rating by remember { mutableFloatStateOf(0f) }

        BakeRoadRatingBar(
            rating = rating,
            onRatingChange = { rating = it }
        )
    }
}