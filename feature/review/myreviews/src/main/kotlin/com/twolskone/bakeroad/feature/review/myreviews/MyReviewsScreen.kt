package com.twolskone.bakeroad.feature.review.myreviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.analytics.LogComposeScreenEvent
import com.twolskone.bakeroad.core.designsystem.component.loading.BakeRoadLoadingScreen
import com.twolskone.bakeroad.core.designsystem.component.loading.LoadingType
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBarIcon
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.ui.EmptyCard
import com.twolskone.bakeroad.feature.review.myreviews.component.MyReviewCard
import com.twolskone.bakeroad.feature.review.myreviews.mvi.MyReviewsState

@Composable
internal fun MyReviewsScreen(
    modifier: Modifier = Modifier,
    state: MyReviewsState,
    listState: LazyListState,
    onBackClick: () -> Unit,
    onBakeryNameClick: (bakeryId: Int, areaCode: Int) -> Unit,
    onLikeClick: (Int, Boolean) -> Unit,
    onImageClick: (reviewIndex: Int, imageIndex: Int) -> Unit
) {
    LogComposeScreenEvent(screen = "MyReviewsScreen")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White)
            .systemBarsPadding()
    ) {
        BakeRoadTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            leftActions = {
                BakeRoadTopAppBarIcon(
                    iconRes = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_back,
                    contentDescription = "Back",
                    onClick = onBackClick
                )
            },
            title = { Text(text = stringResource(id = R.string.feature_review_myreviews)) }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            state = listState
        ) {
            if (state.paging.list.isNotEmpty()) {
                itemsIndexed(
                    items = state.paging.list,
                    key = { _, review -> review.id }
                ) { index, review ->
                    MyReviewCard(
                        review = review,
                        onBakeryNameClick = onBakeryNameClick,
                        onLikeClick = onLikeClick,
                        onImageClick = { imageIndex -> onImageClick(index, imageIndex) }
                    )
                }
            } else {
                item {
                    EmptyCard(
                        modifier = Modifier.fillMaxWidth(),
                        description = stringResource(R.string.feature_review_myreviews_empty_review)
                    )
                }
            }
        }
    }

    BakeRoadLoadingScreen(
        modifier = Modifier.fillMaxSize(),
        isLoading = state.paging.isLoading,
        type = LoadingType.Default
    )
}