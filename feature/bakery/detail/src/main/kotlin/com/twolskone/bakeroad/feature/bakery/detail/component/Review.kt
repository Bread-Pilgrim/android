package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import coil.compose.AsyncImage
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadTextButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.extension.noRippleSingleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.bakery.detail.R

private val CardPadding = 12.dp
private val RawContentSpacing = 6.dp
private val contentPadding = PaddingValues(top = 8.dp, start = CardPadding, end = CardPadding)

/**
 * Review tab.
 */
internal fun LazyListScope.review() {
    item("myReviewHeader") {
        MyReviewHeaderSection(
            modifier = Modifier.fillMaxWidth(),
            reviewList = listOf()
        )
    }
    items(count = 2, contentType = { "myReview" }) {
        ReviewCard(
            modifier = Modifier
                .fillMaxWidth()
                .background(BakeRoadTheme.colorScheme.White)
                .padding(vertical = 6.dp, horizontal = 16.dp)
        )
    }
    item("allReviewHeader") {
        AllReviewHeaderSection(modifier = Modifier.fillMaxWidth())
    }
    items(count = 10, contentType = { "allReview" }) {
        ReviewCard(
            modifier = Modifier
                .fillMaxWidth()
                .background(BakeRoadTheme.colorScheme.White)
                .padding(vertical = 6.dp, horizontal = 16.dp)
                .padding(bottom = if (it == 9) 14.dp else 0.dp)
        )
    }
}

/**
 * Review card used in Home and Review tabs.
 */
@Composable
internal fun ReviewCard(
    modifier: Modifier = Modifier
) {
    var likeState by remember { mutableStateOf(false) }
    val likeColor by animateColorAsState(
        targetValue = if (likeState) BakeRoadTheme.colorScheme.Primary500 else BakeRoadTheme.colorScheme.Gray300.copy()
    )

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardColors(
            containerColor = BakeRoadTheme.colorScheme.Gray40,
            contentColor = BakeRoadTheme.colorScheme.Gray700,
            disabledContainerColor = BakeRoadTheme.colorScheme.Gray100,
            disabledContentColor = BakeRoadTheme.colorScheme.Gray500
        ),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(
                    top = CardPadding,
                    start = CardPadding,
                    end = CardPadding
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_person_image),
                contentDescription = "Profile"
            )
            Text(
                modifier = Modifier
                    .padding(start = 6.dp)
                    .weight(1f),
                text = "서빵글",
                style = BakeRoadTheme.typography.body2XsmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray600)
            )
            Image(
                modifier = Modifier.size(16.dp),
                imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_star_yellow),
                contentDescription = "RatingStar"
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = "4.7",
                style = BakeRoadTheme.typography.bodyXsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray950)
            )
        }
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val cardWidth = (maxWidth / 2) - CardPadding - (RawContentSpacing / 2)
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = contentPadding
            ) {
                items(count = 3) {
                    AsyncImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .width(cardWidth)
                            .aspectRatio(3f / 2f),
                        model = "",
                        contentDescription = "ReviewImage",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_thumbnail)
                    )
                }
            }
        }
        Text(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth(),
            text = "겉은 바삭, 속은 촉촉! 버터 향 가득한 크루아상이 진짜 미쳤어요… 또 가고 싶을 정도 \uD83E\uDD50✨",
            style = BakeRoadTheme.typography.bodyXsmallRegular
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = contentPadding
        ) {
            items(count = 10) {
                BakeRoadChip(
                    selected = false,
                    size = ChipSize.SMALL,
                    label = { Text(text = "꿀고구마휘낭시에") }
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    bottom = CardPadding,
                    start = CardPadding
                )
                .noRippleSingleClickable { likeState = !likeState },
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
                text = stringResource(id = R.string.feature_bakery_detail_like),
                style = BakeRoadTheme.typography.bodyXsmallRegular.copy(color = likeColor)
            )
        }
    }
}

/**
 * Shown when there are no reviews.
 */
@Composable
internal fun EmptyReviewCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardColors(
            containerColor = BakeRoadTheme.colorScheme.Gray40,
            contentColor = BakeRoadTheme.colorScheme.Gray600,
            disabledContainerColor = BakeRoadTheme.colorScheme.Gray40,
            disabledContentColor = BakeRoadTheme.colorScheme.Gray600
        ),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 40.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.feature_bakery_detail_description_empty_review),
            style = BakeRoadTheme.typography.bodyXsmallRegular,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * My reviews header section.
 */
@Composable
private fun MyReviewHeaderSection(
    modifier: Modifier = Modifier,
    reviewList: List<String>
) {
//    val pagerState = rememberPagerState(pageCount = { reviewList.size })

    Column(
        modifier = modifier
            .background(color = BakeRoadTheme.colorScheme.White)
            .padding(top = 20.dp, bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.feature_bakery_detail_my_review),
                style = BakeRoadTheme.typography.bodyLargeSemibold
            )
            Text(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .weight(1f),
                text = stringResource(R.string.feature_bakery_detail_review_count, "100"),
                style = BakeRoadTheme.typography.bodySmallMedium
            )
            BakeRoadOutlinedButton(
                modifier = Modifier,
                style = OutlinedButtonStyle.ASSISTIVE,
                size = ButtonSize.SMALL,
                onClick = {},
                content = { Text(text = stringResource(id = R.string.feature_bakery_detail_button_write_review)) }
            )
        }
//        HorizontalPager(
//            modifier = modifier,
//            state = pagerState,
//            contentPadding = PaddingValues(horizontal = 16.dp),
//            pageSpacing = 8.dp
//        ) {
//            ReviewCard()
//        }
    }
}

/**
 * All reviews header sections. (include my reviews)
 */
@Composable
private fun AllReviewHeaderSection(
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .background(color = BakeRoadTheme.colorScheme.White)
            .padding(top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.feature_bakery_detail_title_review),
                style = BakeRoadTheme.typography.bodyLargeSemibold
            )
            Text(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .weight(1f),
                text = stringResource(R.string.feature_bakery_detail_review_count, "100"),
                style = BakeRoadTheme.typography.bodySmallMedium
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 6.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_star_yellow),
                contentDescription = "RatingStar"
            )
            Text(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .weight(1f),
                text = "5.0",
                style = BakeRoadTheme.typography.bodyMediumMedium.copy(color = BakeRoadTheme.colorScheme.Gray950)
            )
            BakeRoadTextButton(
                style = TextButtonStyle.ASSISTIVE,
                size = TextButtonSize.SMALL,
                onClick = {},
                content = { Text(text = "좋아요 순") }
            )
        }
    }
}

@Composable
private fun EmptyReviewSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = BakeRoadTheme.colorScheme.White)
            .padding(top = 6.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmptyReviewCard(modifier = Modifier.fillMaxWidth())
        BakeRoadSolidButton(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 20.dp)
                .fillMaxWidth(),
            style = SolidButtonStyle.PRIMARY,
            size = ButtonSize.MEDIUM,
            onClick = {},
            content = { Text(text = stringResource(id = R.string.feature_bakery_detail_button_write_review)) }
        )
    }
}

@Preview
@Composable
private fun ReviewCardPreview() {
    BakeRoadTheme {
        ReviewCard()
    }
}

@Preview
@Composable
private fun EmptyReviewCardPreview() {
    BakeRoadTheme {
        EmptyReviewCard(modifier = Modifier.fillMaxWidth())
    }
}

@Preview
@Composable
private fun MyReviewSectionPreview() {
    BakeRoadTheme {
        MyReviewHeaderSection(
            modifier = Modifier.fillMaxWidth(),
            reviewList = listOf("", "")
        )
    }
}

@Preview
@Composable
private fun AllReviewHeaderSectionPreview() {
    BakeRoadTheme {
        AllReviewHeaderSection(modifier = Modifier.fillMaxWidth())
    }
}

@Preview
@Composable
private fun EmptyReviewSectionPreview() {
    BakeRoadTheme {
        EmptyReviewSection(modifier = Modifier.fillMaxWidth())
    }
}