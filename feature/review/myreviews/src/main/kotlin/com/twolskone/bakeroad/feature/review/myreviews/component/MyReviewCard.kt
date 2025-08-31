package com.twolskone.bakeroad.feature.review.myreviews.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import coil.compose.AsyncImage
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.extension.noRippleSingleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.MyBakeryReview
import java.time.LocalDate

private val CardShape = RoundedCornerShape(12.dp)
private val CardPadding = 12.dp
private val contentPadding = PaddingValues(top = 8.dp, start = CardPadding, end = CardPadding)

@Composable
internal fun MyReviewCard(
    modifier: Modifier = Modifier,
    review: MyBakeryReview,
    onLikeClick: (Int, Boolean) -> Unit
) {
    val likeColor by animateColorAsState(
        targetValue = if (review.isLike) {
            BakeRoadTheme.colorScheme.Primary500
        } else {
            BakeRoadTheme.colorScheme.Gray300
        }
    )

    Card(
        modifier = modifier,
        shape = CardShape,
        colors = CardColors(
            containerColor = BakeRoadTheme.colorScheme.Gray40,
            contentColor = BakeRoadTheme.colorScheme.Gray700,
            disabledContainerColor = BakeRoadTheme.colorScheme.Gray100,
            disabledContentColor = BakeRoadTheme.colorScheme.Gray500
        ),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(top = CardPadding, start = CardPadding, end = CardPadding)
                .fillMaxWidth()
        ) {
            // 빵집 이름
            Text(
                modifier = Modifier,
                text = review.bakeryName,
                style = BakeRoadTheme.typography.bodyMediumSemibold.copy(color = BakeRoadTheme.colorScheme.Black)
            )
            // 별점
            Row(modifier = Modifier.padding(top = 4.dp)) {
                Image(
                    modifier = Modifier.size(16.dp),
                    imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_star_yellow),
                    contentDescription = "RatingStar"
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = review.rating.toString(),
                    style = BakeRoadTheme.typography.bodyXsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray950)
                )
            }
        }
        // 사진
        if (review.photos.isNotEmpty()) {
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val cardWidth = (maxWidth / 2) - CardPadding - (7.dp / 2)
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                    contentPadding = contentPadding
                ) {
                    items(
                        items = review.photos,
                        key = { url -> url }
                    ) { url ->
                        AsyncImage(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .width(cardWidth)
                                .aspectRatio(3f / 2f),
                            model = url,
                            contentDescription = "ReviewImage",
                            placeholder = painterResource(id = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_thumbnail),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
        // 내용
        Text(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth(),
            text = review.content,
            style = BakeRoadTheme.typography.bodyXsmallRegular
        )
        if (review.menus.isNotEmpty()) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                review.menus.fastForEach { menu ->
                    BakeRoadChip(
                        selected = false,
                        size = ChipSize.SMALL,
                        label = { Text(text = menu) }
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 16.dp, bottom = CardPadding, start = CardPadding, end = CardPadding)
                .noRippleSingleClickable { onLikeClick(review.id, !review.isLike) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_heart_fill),
                contentDescription = "Like",
                tint = likeColor
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = if (review.likeCount <= 0) {
                    stringResource(com.twolskone.bakeroad.core.ui.R.string.core_ui_label_like)
                } else {
                    review.likeCount.toString()
                },
                style = BakeRoadTheme.typography.bodyXsmallRegular.copy(color = likeColor)
            )
            Text(
                modifier = Modifier.weight(1f),
                text = review.date?.let { "${it.year}.${it.monthValue}.${it.dayOfMonth}" }.orEmpty(),
                style = BakeRoadTheme.typography.body2XsmallRegular,
                color = BakeRoadTheme.colorScheme.Gray300,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview
@Composable
private fun ReviewCardPreview() {
    BakeRoadTheme {
        MyReviewCard(
            modifier = Modifier.fillMaxWidth(),
            review = MyBakeryReview(
                id = 1,
                bakeryId = 1,
                bakeryName = "서라당",
                isLike = false,
                content = "겉은 바삭, 속은 촉촉! 버터 향 가득한 크루아상이 진짜 미쳤어요… 또 가고 싶을 정도 \uD83E\uDD50✨",
                rating = 5.0f,
                likeCount = 100,
                date = LocalDate.now(),
                menus = listOf("꿀고구마휘낭시에", "꿀고구마휘낭시에", "꿀고구마휘낭시에", "꿀고구마휘낭시에", "꿀고구마휘낭시에"),
                photos = emptyList()
            ),
            onLikeClick = { _, _ -> }
        )
    }
}