package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.twolskone.bakeroad.core.common.android.base.extension.emptyState
import com.twolskone.bakeroad.core.common.kotlin.extension.toCommaString
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadTextButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonStyle
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.type.ReviewSortType
import com.twolskone.bakeroad.feature.bakery.detail.R
import com.twolskone.bakeroad.feature.bakery.detail.model.ReviewTab
import com.twolskone.bakeroad.feature.bakery.detail.mvi.ReviewState
import kotlinx.collections.immutable.toImmutableList

/**
 * Review section
 */
internal fun LazyListScope.review(
    state: ReviewState,
    sortType: ReviewSortType,
    myReviewPaging: LazyPagingItems<BakeryReview>,
    reviewPaging: LazyPagingItems<BakeryReview>,
    onReviewTabSelect: (ReviewTab) -> Unit,
    onSortClick: () -> Unit
) {
    item {
        Box(modifier = Modifier.background(color = BakeRoadTheme.colorScheme.White)) {
            ReviewMultiToggleButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp, bottom = 4.dp)
                    .height(48.dp),
                selectedOptionIndex = state.tab.ordinal,
                optionList = ReviewTab.entries.map { stringResource(id = it.labelId) }.toImmutableList(),
                onSelect = { index ->
                    val selectedReviewTab = runCatching { ReviewTab.entries[index] }.getOrDefault(ReviewTab.ALL_REVIEW)
                    if (index != state.tab.ordinal) {
                        when (selectedReviewTab) {
                            ReviewTab.MY_REVIEW -> myReviewPaging.refresh()
                            ReviewTab.ALL_REVIEW -> reviewPaging.refresh()
                        }
                        onReviewTabSelect(selectedReviewTab)
                    }
                }
            )
        }
    }
    when (state.tab) {
        ReviewTab.MY_REVIEW -> {
            item("myReviewHeader") {
                MyReviewHeader(
                    modifier = Modifier.fillMaxWidth(),
                    state = state
                )
            }
            if (myReviewPaging.emptyState) {
                item {
                    EmptyReviewCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BakeRoadTheme.colorScheme.White)
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                    )
                }
            } else {
                items(count = myReviewPaging.itemCount, contentType = { "myReview" }) { index ->
                    myReviewPaging[index]?.let { review ->
                        ReviewCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BakeRoadTheme.colorScheme.White)
                                .padding(vertical = 6.dp, horizontal = 16.dp),
                            review = review
                        )
                    }
                }
            }
        }

        ReviewTab.ALL_REVIEW -> {
            item("allReviewHeader") {
                AllReviewHeader(
                    modifier = Modifier.fillMaxWidth(),
                    state = state,
                    reviewSort = sortType,
                    onSortClick = onSortClick
                )
            }
            if (reviewPaging.emptyState) {
                item {
                    EmptyReviewCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BakeRoadTheme.colorScheme.White)
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                    )
                }
            } else {
                items(count = reviewPaging.itemCount, contentType = { "allReview" }) { index ->
                    reviewPaging[index]?.let { review ->
                        ReviewCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BakeRoadTheme.colorScheme.White)
                                .padding(vertical = 6.dp, horizontal = 16.dp),
                            review = review
                        )
                    }
                }
            }
        }
    }
}

/**
 * My reviews header
 */
@Composable
private fun MyReviewHeader(
    modifier: Modifier = Modifier,
    state: ReviewState
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
                text = stringResource(R.string.feature_bakery_detail_title_my_review),
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
                style = OutlinedButtonStyle.ASSISTIVE,
                size = ButtonSize.SMALL,
                onClick = {},
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
    onSortClick: () -> Unit
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
                style = OutlinedButtonStyle.ASSISTIVE,
                size = ButtonSize.SMALL,
                onClick = {},
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
            state = ReviewState()
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
            onSortClick = {}
        )
    }
}