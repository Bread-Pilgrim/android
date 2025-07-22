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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.util.fastForEachIndexed
import coil.compose.AsyncImage
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonStyle
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.BakeryDetail
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
    menuList: ImmutableList<BakeryDetail.Menu>
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
                    .fillMaxWidth()
            )
            TourAreaSection(modifier = Modifier.fillMaxWidth())
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
private fun ReviewSection(modifier: Modifier = Modifier) {
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
            Image(
                modifier = Modifier.size(20.dp),
                imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_star_yellow),
                contentDescription = "RatingStar"
            )
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "5.0",
                style = BakeRoadTheme.typography.bodyMediumSemibold.copy(color = BakeRoadTheme.colorScheme.Gray950)
            )
        }
        ReviewPager(reviewList = listOf("", "", ""))
        BakeRoadOutlinedButton(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            style = OutlinedButtonStyle.ASSISTIVE,
            size = ButtonSize.MEDIUM,
            onClick = {},
            content = { Text(text = stringResource(R.string.feature_bakery_detail_button_view_all_review)) }
        )
    }
}

/**
 * TourArea section.
 */
@Composable
private fun TourAreaSection(modifier: Modifier = Modifier) {
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
            items(count = 5) {
                SimpleTourAreaCard()
            }
        }
    }
}

@Composable
private fun ReviewPager(
    modifier: Modifier = Modifier,
    reviewList: List<String>
) {
    val pagerState = rememberPagerState(pageCount = { reviewList.size })

    Column {
        HorizontalPager(
            modifier = modifier,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 8.dp
        ) {
            ReviewCard()
        }
        ReviewPagerIndicator(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(),
            pageCount = reviewList.size,
            currentPageIndex = 0
        )
    }
}

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

@Composable
private fun SimpleTourAreaCard(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(100.dp),
            model = "",
            contentDescription = "TourArea",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_thumbnail)
        )
        Text(
            modifier = Modifier.padding(top = 6.dp),
            text = "부평 깡통시장",
            style = BakeRoadTheme.typography.bodySmallSemibold.copy(color = BakeRoadTheme.colorScheme.Gray990),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.padding(top = 3.dp),
            text = "전통시장, 광복동",
            style = BakeRoadTheme.typography.body2XsmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray400),
            maxLines = 1,
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
        ReviewSection(modifier = Modifier.fillMaxWidth())
    }
}

@Preview
@Composable
private fun TourAreaSectionPreview() {
    BakeRoadTheme {
        TourAreaSection(modifier = Modifier.fillMaxWidth())
    }
}