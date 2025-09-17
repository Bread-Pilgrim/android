package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.twolskone.bakeroad.core.common.android.extension.emptyState
import com.twolskone.bakeroad.core.common.kotlin.extension.toCommaString
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadTextButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonRole
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonStyle
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.type.ReviewSortType
import com.twolskone.bakeroad.core.ui.EmptyCard
import com.twolskone.bakeroad.feature.bakery.detail.R
import com.twolskone.bakeroad.feature.bakery.detail.model.ReviewTab
import com.twolskone.bakeroad.feature.bakery.detail.mvi.ReviewState
import kotlinx.collections.immutable.toImmutableList

/**
 * Review section
 */
internal fun LazyListScope.review(
    state: ReviewState,
    tabState: ReviewTab,
    sortState: ReviewSortType,
    myReviewPaging: LazyPagingItems<BakeryReview>,
    reviewPaging: LazyPagingItems<BakeryReview>,
    onReviewTabSelect: (ReviewTab) -> Unit,
    onSortClick: () -> Unit,
    onWriteReviewClick: () -> Unit,
    onReviewLikeClick: (Int, Boolean) -> Unit,
    onReviewImageClick: (List<String>, Int) -> Unit
) {
    item {
        Box(modifier = Modifier.background(color = BakeRoadTheme.colorScheme.White)) {
            ReviewMultiToggleButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp, bottom = 4.dp)
                    .height(48.dp),
                selectedOptionIndex = tabState.ordinal,
                optionList = ReviewTab.entries.map { stringResource(id = it.labelRes) }.toImmutableList(),
                onSelect = { index ->
                    val selectedReviewTab = runCatching { ReviewTab.entries[index] }.getOrDefault(ReviewTab.ALL_REVIEW)
                    if (index != tabState.ordinal) {
                        onReviewTabSelect(selectedReviewTab)
                    }
                }
            )
        }
    }
    when (tabState) {
        ReviewTab.ALL_REVIEW -> {
            item {
                AllReviewHeader(
                    modifier = Modifier.fillMaxWidth(),
                    state = state,
                    reviewSort = sortState,
                    onSortClick = onSortClick,
                    onWriteReviewClick = onWriteReviewClick
                )
            }
            if (reviewPaging.emptyState) {
                item("emptyReview") {
                    EmptyCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BakeRoadTheme.colorScheme.White)
                            .padding(vertical = 6.dp, horizontal = 16.dp)
                            .padding(bottom = 14.dp),
                        description = stringResource(id = R.string.feature_bakery_detail_empty_review)
                    )
                }
            } else {
                items(
                    count = reviewPaging.itemCount,
                    key = reviewPaging.itemKey { it.id }
                ) { index ->
                    reviewPaging[index]?.let { review ->
                        ReviewCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BakeRoadTheme.colorScheme.White)
                                .padding(vertical = 6.dp, horizontal = 16.dp),
                            review = review,
                            localLikeMap = state.localLikeMap,
                            onLikeClick = onReviewLikeClick,
                            onImageClick = { imageIndex -> onReviewImageClick(review.photos, imageIndex) }
                        )
                    }
                }
            }
        }

        ReviewTab.MY_REVIEW -> {
            item {
                MyReviewHeader(
                    modifier = Modifier.fillMaxWidth(),
                    onWriteReviewClick = onWriteReviewClick
                )
            }
            if (myReviewPaging.emptyState) {
                item("emptyReview") {
                    EmptyCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BakeRoadTheme.colorScheme.White)
                            .padding(vertical = 6.dp, horizontal = 16.dp)
                            .padding(bottom = 14.dp),
                        description = stringResource(id = R.string.feature_bakery_detail_empty_review)
                    )
                }
            } else {
                items(
                    count = myReviewPaging.itemCount,
                    key = myReviewPaging.itemKey { it.id }
                ) { index ->
                    myReviewPaging[index]?.let { review ->
                        ReviewCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BakeRoadTheme.colorScheme.White)
                                .padding(vertical = 6.dp, horizontal = 16.dp),
                            review = review,
                            localLikeMap = state.localLikeMap,
                            onLikeClick = onReviewLikeClick,
                            onImageClick = { imageIndex -> onReviewImageClick(review.photos, imageIndex) }
                        )
                    }
                }
            }
        }
    }
    item {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .fillParentMaxHeight(fraction = 0.65f)
                .background(color = BakeRoadTheme.colorScheme.White)
        )
    }
}

/**
 * My reviews header
 */
@Composable
private fun MyReviewHeader(
    modifier: Modifier = Modifier,
    onWriteReviewClick: () -> Unit
) {
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
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.feature_bakery_detail_title_my_review),
                style = BakeRoadTheme.typography.bodyLargeSemibold
            )
            BakeRoadOutlinedButton(
                modifier = Modifier,
                role = OutlinedButtonRole.ASSISTIVE,
                size = ButtonSize.SMALL,
                onClick = onWriteReviewClick,
                content = { Text(text = stringResource(id = R.string.feature_bakery_detail_button_write_review)) }
            )
        }
    }
}

/**
 * All reviews header (include my reviews)
 */
@Composable
private fun AllReviewHeader(
    modifier: Modifier,
    state: ReviewState,
    reviewSort: ReviewSortType,
    onSortClick: () -> Unit,
    onWriteReviewClick: () -> Unit
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
                text = stringResource(R.string.feature_bakery_detail_review_count, state.count.toCommaString()),
                style = BakeRoadTheme.typography.bodySmallMedium
            )
            BakeRoadOutlinedButton(
                modifier = Modifier,
                role = OutlinedButtonRole.ASSISTIVE,
                size = ButtonSize.SMALL,
                onClick = onWriteReviewClick,
                content = { Text(text = stringResource(id = R.string.feature_bakery_detail_button_write_review)) }
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
                text = state.avgRating.toString(),
                style = BakeRoadTheme.typography.bodyMediumMedium.copy(color = BakeRoadTheme.colorScheme.Gray950)
            )
            BakeRoadTextButton(
                style = TextButtonStyle.ASSISTIVE,
                size = TextButtonSize.SMALL,
                onClick = onSortClick,
                content = { Text(text = reviewSort.label) }
            )
        }
    }
}

@Preview
@Composable
private fun MyReviewSectionPreview() {
    BakeRoadTheme {
        MyReviewHeader(
            modifier = Modifier.fillMaxWidth(),
            onWriteReviewClick = {}
        )
    }
}

@Preview
@Composable
private fun AllReviewHeaderSectionPreview() {
    BakeRoadTheme {
        AllReviewHeader(
            modifier = Modifier.fillMaxWidth(),
            state = ReviewState(),
            reviewSort = ReviewSortType.LIKE_COUNT_DESC,
            onSortClick = {},
            onWriteReviewClick = {}
        )
    }
}