package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import com.twolskone.bakeroad.core.common.kotlin.extension.toCommaString
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonRole
import com.twolskone.bakeroad.core.designsystem.component.skeleton.ReviewsSkeleton
import com.twolskone.bakeroad.core.designsystem.component.skeleton.TitleSkeleton
import com.twolskone.bakeroad.core.designsystem.extension.shimmerEffect
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.core.ui.EmptyCard
import com.twolskone.bakeroad.feature.bakery.detail.R
import com.twolskone.bakeroad.feature.bakery.detail.mvi.LoadingState
import java.time.LocalDate
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

private const val MenuMaxCount = 4
private const val TourAreaMaxCount = 5

/**
 * Home section (Menu, Review, TourArea)
 * @see MenuContent
 * @see ReviewContent
 * @see TourAreaContent
 */
internal fun LazyListScope.home(
    itemModifier: Modifier = Modifier,
    loadingState: LoadingState,
    reviewCount: Int,
    menuList: ImmutableList<BakeryDetail.Menu>,
    reviewList: ImmutableList<BakeryReview>,
    tourAreaList: ImmutableList<TourArea>,
    localReviewLikeMap: PersistentMap<Int, Boolean>,
    onViewAllMenuClick: () -> Unit,
    onViewAllReviewClick: () -> Unit,
    onViewAllTourAreaClick: () -> Unit,
    onReviewLikeClick: (Int, Boolean) -> Unit,
    onTourAreaClick: (TourArea) -> Unit,
    onMenuImageClick: (Int) -> Unit,
    onReviewImageClick: (Int, Int) -> Unit
) {
    item {
        Column(modifier = itemModifier) {
            if (loadingState.bakeryDetailLoading) {
                MenuContentSkeleton()
            } else {
                MenuContent(
                    modifier = Modifier.fillMaxWidth(),
                    menuList = menuList,
                    onViewAllClick = onViewAllMenuClick,
                    onImageClick = onMenuImageClick
                )
            }
            if (loadingState.previewReviewLoading) {
                ReviewContentSkeleton()
            } else {
                ReviewContent(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    reviewCount = reviewCount,
                    reviewList = reviewList,
                    onViewAllClick = onViewAllReviewClick,
                    localReviewLikeMap = localReviewLikeMap,
                    onLikeClick = onReviewLikeClick,
                    onImageClick = onReviewImageClick
                )
            }
            if (loadingState.tourAreaLoading) {
                TourAreaContentSkeleton()
            } else {
                TourAreaContent(
                    modifier = Modifier.fillMaxWidth(),
                    tourAreaList = tourAreaList,
                    onViewAllClick = onViewAllTourAreaClick,
                    onTourAreaClick = onTourAreaClick
                )
            }
        }
    }
}

/**
 * Menu content
 * 최대 4개
 */
@Composable
private fun MenuContent(
    modifier: Modifier = Modifier,
    menuList: ImmutableList<BakeryDetail.Menu>,
    onViewAllClick: () -> Unit,
    onImageClick: (Int) -> Unit
) {
    val lastIndex = menuList.take(4).size - 1

    Column(
        modifier = modifier
            .background(color = BakeRoadTheme.colorScheme.White)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.feature_bakery_detail_title_recommend_menu),
                style = BakeRoadTheme.typography.bodyLargeSemibold
            )
            if (menuList.any { it.imageUrl.isNotBlank() }) {
                Text(
                    modifier = Modifier.singleClickable { onImageClick(0) },
                    text = stringResource(R.string.feature_bakery_detail_button_view_image),
                    style = BakeRoadTheme.typography.bodyXsmallMedium.copy(
                        color = BakeRoadTheme.colorScheme.Gray950,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }
        menuList.take(MenuMaxCount).fastForEachIndexed { index, menu ->
            MenuListItem(
                modifier = Modifier.fillMaxWidth(),
                menu = menu,
                onImageClick = { onImageClick(index) }
            )
            if (index != lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = BakeRoadTheme.colorScheme.Gray50
                )
            }
        }
        if (menuList.isNotEmpty()) {
            BakeRoadOutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(),
                role = OutlinedButtonRole.ASSISTIVE,
                size = ButtonSize.MEDIUM,
                onClick = onViewAllClick,
                content = { Text(text = stringResource(R.string.feature_bakery_detail_button_view_all_menu)) }
            )
        }
    }
}

/**
 * Review content
 * 최대 3개
 */
@Composable
private fun ReviewContent(
    modifier: Modifier = Modifier,
    reviewCount: Int,
    reviewList: ImmutableList<BakeryReview>,
    localReviewLikeMap: PersistentMap<Int, Boolean>,
    onViewAllClick: () -> Unit,
    onLikeClick: (Int, Boolean) -> Unit,
    onImageClick: (reviewIndex: Int, imageIndex: Int) -> Unit
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
            reviewList.fastForEachIndexed { index, review ->
                ReviewCard(
                    modifier = Modifier.fillMaxWidth(),
                    review = review,
                    localLikeMap = localReviewLikeMap,
                    onLikeClick = onLikeClick,
                    onImageClick = { imageIndex -> onImageClick(index, imageIndex) }
                )
            }
            BakeRoadOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                role = OutlinedButtonRole.ASSISTIVE,
                size = ButtonSize.MEDIUM,
                onClick = onViewAllClick,
                content = { Text(text = stringResource(R.string.feature_bakery_detail_button_view_all_review)) }
            )
        } else {
            EmptyCard(
                modifier = Modifier.fillMaxWidth(),
                description = stringResource(id = R.string.feature_bakery_detail_empty_review)
            )
        }
    }
}

/**
 * TourArea
 * 최대 5개
 */
@Composable
private fun TourAreaContent(
    modifier: Modifier = Modifier,
    tourAreaList: ImmutableList<TourArea>,
    onViewAllClick: () -> Unit,
    onTourAreaClick: (TourArea) -> Unit
) {
    Column(
        modifier = modifier
            .background(color = BakeRoadTheme.colorScheme.White)
            .padding(vertical = 20.dp)
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.feature_bakery_detail_title_tour_area),
                style = BakeRoadTheme.typography.bodyLargeSemibold
            )
        }
        if (tourAreaList.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                tourAreaList.take(TourAreaMaxCount).fastForEach { tourArea ->
                    SimpleTourAreaCard(
                        tourArea = tourArea,
                        onClick = onTourAreaClick
                    )
                }
            }
            BakeRoadOutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                role = OutlinedButtonRole.ASSISTIVE,
                size = ButtonSize.MEDIUM,
                onClick = onViewAllClick,
                content = { Text(text = stringResource(R.string.feature_bakery_detail_button_view_all_tour_area)) }
            )
        }
    }
}

@Composable
private fun MenuContentSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BakeRoadTheme.colorScheme.White)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleSkeleton(
            modifier = Modifier.fillMaxWidth(),
            titleWidth = 154.dp
        )
        repeat(4) { index ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .size(width = 58.dp, height = 23.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .shimmerEffect()
                    )
                    Box(
                        modifier = Modifier
                            .size(width = 97.dp, height = 16.dp)
                            .clip(CircleShape)
                            .shimmerEffect()
                    )
                    Box(
                        modifier = Modifier
                            .size(width = 76.dp, height = 16.dp)
                            .clip(CircleShape)
                            .shimmerEffect()
                    )
                }
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .aspectRatio(ratio = 5f / 4f)
                        .clip(RoundedCornerShape(8.dp))
                        .shimmerEffect()
                )
            }
            if (index < 3) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .shimmerEffect()
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerEffect()
        )
    }
}

@Composable
private fun ReviewContentSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BakeRoadTheme.colorScheme.White)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleSkeleton(
            modifier = Modifier.fillMaxWidth(),
            titleWidth = 154.dp
        )
        ReviewsSkeleton(modifier = Modifier.fillMaxWidth())
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerEffect()
        )
    }
}

@Composable
private fun TourAreaContentSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BakeRoadTheme.colorScheme.White)
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleSkeleton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            titleWidth = 154.dp,
            skipButton = true
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            repeat(5) {
                Column {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .shimmerEffect()
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .size(width = 97.dp, height = 12.25.dp)
                            .clip(CircleShape)
                            .shimmerEffect()
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(width = 76.dp, height = 12.25.dp)
                            .clip(CircleShape)
                            .shimmerEffect()
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerEffect()
        )
    }
}

@Preview
@Composable
private fun MenuContentPreview() {
    BakeRoadTheme {
        val isLoading = true
        if (isLoading) {
            MenuContentSkeleton()
        } else {
            MenuContent(
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
                ),
                onViewAllClick = {},
                onImageClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun ReviewContentPreview() {
    BakeRoadTheme {
        val isLoading = true
        if (isLoading) {
            ReviewContentSkeleton()
        } else {
            ReviewContent(
                modifier = Modifier.fillMaxWidth(),
                reviewCount = 0,
                reviewList = persistentListOf(
                    BakeryReview(
                        id = 1,
                        avgRating = 4.7f,
                        totalCount = 0,
                        userName = "서빵글",
                        profileUrl = "",
                        isLike = false,
                        content = "겉은 바삭, 속은 촉촉! 버터 향 가득한 크루아상이 진짜 미쳤어요… 또 가고 싶을 정도 \uD83E\uDD50✨",
                        rating = 5.0f,
                        likeCount = 100,
                        date = LocalDate.now(),
                        menus = listOf("꿀고구마휘낭시에", "꿀고구마휘낭시에", "꿀고구마휘낭시에", "꿀고구마휘낭시에", "꿀고구마휘낭시에"),
                        photos = emptyList()
                    )
                ),
                localReviewLikeMap = persistentMapOf(),
                onViewAllClick = {},
                onLikeClick = { _, _ -> },
                onImageClick = { _, _ -> }
            )
        }
    }
}

@Preview
@Composable
private fun TourAreaContentPreview() {
    BakeRoadTheme {
        val isLoading = false
        if (isLoading) {
            TourAreaContentSkeleton()
        } else {
            TourAreaContent(
                modifier = Modifier.fillMaxWidth(),
                tourAreaList = persistentListOf(
                    TourArea(
                        title = "관광지명\n관광지명",
                        type = "타입",
                        address = "주소주소주소주소",
                        imagePath = "",
                        latitude = 0f,
                        longitude = 0f
                    )
                ),
                onViewAllClick = {},
                onTourAreaClick = {}
            )
        }
    }
}