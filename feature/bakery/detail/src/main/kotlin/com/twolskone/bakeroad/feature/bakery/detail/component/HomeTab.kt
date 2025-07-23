package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import coil.compose.AsyncImage
import com.twolskone.bakeroad.core.common.kotlin.extension.toCommaString
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.feature.bakery.detail.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Home tab with sections. (Menu, Review, TourArea)
 * @see MenuSection
 * @see ReviewSection
 * @see TourAreaSection
 */
internal fun LazyListScope.home(
    itemModifier: Modifier = Modifier,
    reviewCount: Int,
    menuList: ImmutableList<BakeryDetail.Menu>,
    reviewList: ImmutableList<BakeryReview>,
    tourAreaList: ImmutableList<TourArea>
) {
    item(contentType = "home") {
        Column(modifier = itemModifier) {
            MenuSection(
                modifier = Modifier.fillMaxWidth(),
                menuList = menuList
            )
            ReviewSection(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                reviewCount = reviewCount,
                reviewList = reviewList
            )
            TourAreaSection(
                modifier = Modifier.fillMaxWidth(),
                tourAreaList = tourAreaList
            )
        }
    }
}

/**
 * Menu section.
 */
@Composable
private fun MenuSection(
    modifier: Modifier = Modifier,
    menuList: ImmutableList<BakeryDetail.Menu>
) {
    val lastIndex = menuList.take(4).size - 1

    Column(
        modifier = modifier
            .background(color = BakeRoadTheme.colorScheme.White)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.feature_bakery_detail_title_recommend_menu),
                style = BakeRoadTheme.typography.bodyLargeSemibold
            )
        }
        menuList.take(4).fastForEachIndexed { index, menu ->
            Menu(modifier = Modifier.fillMaxWidth(), menu = menu)
            if (index != lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = BakeRoadTheme.colorScheme.Gray50
                )
            }
        }
        BakeRoadOutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            style = OutlinedButtonStyle.ASSISTIVE,
            size = ButtonSize.MEDIUM,
            onClick = {},
            content = { Text(text = stringResource(R.string.feature_bakery_detail_button_view_all_menu)) }
        )
    }
}

/**
 * Review section with pager.
 */
@Composable
private fun ReviewSection(
    modifier: Modifier = Modifier,
    reviewCount: Int,
    reviewList: ImmutableList<BakeryReview>
) {
    Column(
        modifier = modifier
            .background(color = BakeRoadTheme.colorScheme.White)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                text = stringResource(R.string.feature_bakery_detail_review_count, reviewCount.toCommaString()),
                style = BakeRoadTheme.typography.bodySmallMedium
            )
            Image(
                modifier = Modifier.size(20.dp),
                imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_star_yellow),
                contentDescription = "RatingStar"
            )
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = (reviewList.firstOrNull()?.avgRating ?: 0.0f).toString(),
                style = BakeRoadTheme.typography.bodyMediumSemibold.copy(color = BakeRoadTheme.colorScheme.Gray950)
            )
        }
        if (reviewList.isNotEmpty()) {
            reviewList.fastForEach { review ->
                ReviewCard(
                    modifier = Modifier.fillMaxWidth(),
                    review = review
                )
            }
            BakeRoadOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                style = OutlinedButtonStyle.ASSISTIVE,
                size = ButtonSize.MEDIUM,
                onClick = {},
                content = { Text(text = stringResource(R.string.feature_bakery_detail_button_view_all_review)) }
            )
        } else {
            EmptyReviewCard(modifier = Modifier.fillMaxWidth())
        }
    }
}

/**
 * TourArea section.
 */
@Composable
private fun TourAreaSection(
    modifier: Modifier = Modifier,
    tourAreaList: ImmutableList<TourArea>
) {
    Column(
        modifier = modifier
            .background(color = BakeRoadTheme.colorScheme.White)
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.feature_bakery_detail_title_tour_area),
                style = BakeRoadTheme.typography.bodyLargeSemibold
            )
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = tourAreaList,
                key = { tourArea -> "${tourArea.title}/${tourArea.type}(${tourArea.mapX},${tourArea.mapY})" }
            ) {
                SimpleTourAreaCard(
                    modifier = Modifier.width(TourAreaImageSize),
                    tourArea = it
                )
            }
        }
    }
}

//@Composable
//private fun ReviewPager(
//    modifier: Modifier = Modifier,
//    reviewList: List<String>
//) {
//    val pagerState = rememberPagerState(pageCount = { reviewList.size })
//
//    Column {
//        HorizontalPager(
//            modifier = modifier,
//            state = pagerState,
//            contentPadding = PaddingValues(horizontal = 16.dp),
//            pageSpacing = 8.dp
//        ) {
//            ReviewCard()
//        }
//        ReviewPagerIndicator(
//            modifier = Modifier
//                .padding(top = 12.dp)
//                .fillMaxWidth(),
//            pageCount = reviewList.size,
//            currentPageIndex = 0
//        )
//    }
//}

@Composable
private fun ReviewPagerIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPageIndex: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->
            val color = if (currentPageIndex == iteration) BakeRoadTheme.colorScheme.Primary500 else BakeRoadTheme.colorScheme.Gray100
            Box(
                modifier = Modifier
                    .padding(3.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(8.dp)
            )
        }
    }
}

private val TourAreaImageSize = 100.dp

@Composable
private fun SimpleTourAreaCard(
    modifier: Modifier = Modifier,
    tourArea: TourArea
) {
    Column(modifier = modifier) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(TourAreaImageSize),
                model = tourArea.imagePath,
                contentDescription = "TourArea",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_thumbnail)
            )
            BakeRoadChip(
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.TopStart),
                color = ChipColor.SUB,
                size = ChipSize.SMALL,
                selected = true,
                label = { Text(text = tourArea.type) }
            )
        }
        Text(
            modifier = Modifier.padding(top = 6.dp),
            text = tourArea.title,
            style = BakeRoadTheme.typography.bodySmallSemibold.copy(color = BakeRoadTheme.colorScheme.Gray990),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.padding(top = 3.dp),
            text = tourArea.address,
            style = BakeRoadTheme.typography.body2XsmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray400),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun MenuSectionPreview() {
    BakeRoadTheme {
        MenuSection(
            modifier = Modifier.fillMaxWidth(),
            menuList = persistentListOf(
                BakeryDetail.Menu(
                    name = "에그타르트",
                    price = 3000,
                    isSignature = true,
                    imageUrl = ""
                ),
                BakeryDetail.Menu(
                    name = "에그타르트",
                    price = 3000,
                    isSignature = false,
                    imageUrl = ""
                )
            )
        )
    }
}

@Preview
@Composable
private fun ReviewSectionPreview() {
    BakeRoadTheme {
        ReviewSection(
            modifier = Modifier.fillMaxWidth(),
            reviewCount = 0,
            reviewList = persistentListOf(
                BakeryReview(
                    id = 1,
                    avgRating = 4.7f,
                    userName = "서빵글",
                    profileUrl = "",
                    isLike = false,
                    content = "겉은 바삭, 속은 촉촉! 버터 향 가득한 크루아상이 진짜 미쳤어요… 또 가고 싶을 정도 \uD83E\uDD50✨",
                    rating = 5.0f,
                    likeCount = 100,
                    menus = listOf("꿀고구마휘낭시에", "꿀고구마휘낭시에", "꿀고구마휘낭시에", "꿀고구마휘낭시에", "꿀고구마휘낭시에"),
                    photos = emptyList()
                )
            )
        )
    }
}

@Preview
@Composable
private fun TourAreaSectionPreview() {
    BakeRoadTheme {
        TourAreaSection(
            modifier = Modifier.fillMaxWidth(),
            tourAreaList = persistentListOf(
                TourArea(
                    title = "관광지명\n관광지명",
                    type = "타입",
                    address = "주소주소주소주소",
                    imagePath = "",
                    mapY = 0f,
                    mapX = 0f
                )
            )
        )
    }
}