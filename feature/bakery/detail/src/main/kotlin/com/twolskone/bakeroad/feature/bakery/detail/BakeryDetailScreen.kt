package com.twolskone.bakeroad.feature.bakery.detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.util.fastForEach
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.twolskone.bakeroad.core.common.kotlin.extension.orFalse
import com.twolskone.bakeroad.core.designsystem.component.tab.BakeRoadScrollableTabRow
import com.twolskone.bakeroad.core.designsystem.component.tab.BakeRoadTab
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBarIcon
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus
import com.twolskone.bakeroad.core.model.type.ReviewSortType
import com.twolskone.bakeroad.core.ui.LikeIcon
import com.twolskone.bakeroad.core.ui.LikeIconColors
import com.twolskone.bakeroad.feature.bakery.detail.component.BakeryImageHeader
import com.twolskone.bakeroad.feature.bakery.detail.component.BakeryImageHeaderSkeleton
import com.twolskone.bakeroad.feature.bakery.detail.component.BakeryInfoSection
import com.twolskone.bakeroad.feature.bakery.detail.component.BakeryInfoSectionSkeleton
import com.twolskone.bakeroad.feature.bakery.detail.component.ReviewSortBottomSheet
import com.twolskone.bakeroad.feature.bakery.detail.component.home
import com.twolskone.bakeroad.feature.bakery.detail.component.menu
import com.twolskone.bakeroad.feature.bakery.detail.component.review
import com.twolskone.bakeroad.feature.bakery.detail.component.tourArea
import com.twolskone.bakeroad.feature.bakery.detail.model.BakeryDetailTab
import com.twolskone.bakeroad.feature.bakery.detail.model.ReviewTab
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailState
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

private val TopAppBarHeight = 56.dp

private const val TabsIndex = 2

@Composable
internal fun BakeryDetailScreen(
    modifier: Modifier = Modifier,
    state: BakeryDetailState,
    tabState: BakeryDetailTab,
    reviewTabState: ReviewTab,
    reviewSortState: ReviewSortType,
    myReviewPagingItems: LazyPagingItems<BakeryReview>,
    reviewPagingItems: LazyPagingItems<BakeryReview>,
    onTabSelect: (BakeryDetailTab) -> Unit,
    onReviewTabSelect: (ReviewTab) -> Unit,
    onReviewSortSelect: (ReviewSortType) -> Unit,
    onWriteReviewClick: () -> Unit,
    onBakeryLikeClick: (Boolean) -> Unit,
    onReviewLikeClick: (Int, Boolean) -> Unit,
    onBackClick: () -> Unit
) {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val maxTopPadding = /*statusBarHeight + */TopAppBarHeight
    val headerWidthPx = windowInfo.containerSize.width
    val headerHeightPx = headerWidthPx / 3 * 2 + (with(density) { statusBarHeight.roundToPx() })
    val listState = rememberLazyListState()
    val scrollIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val scrollOffset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    val topPadding by remember {
        derivedStateOf {
            when {
                scrollIndex < 1 -> 0.dp

                scrollIndex == 1 -> {
                    val factor = scrollOffset.coerceIn(0, 700) / 700f
                    (factor * maxTopPadding)
                }

                else -> maxTopPadding
            }
        }
    }
    val topBarColorTransition by remember {
        derivedStateOf {
            val transition = when {
                scrollIndex == 0 && scrollOffset < headerHeightPx / 2 -> 0f
                scrollIndex == 0 -> (scrollOffset / headerHeightPx.toFloat()).coerceIn(0f, 1f)
                else -> 1f
            }

            transition.coerceIn(0f, 1f)
        }
    }
    val topBarColor by animateColorAsState(
        targetValue = BakeRoadTheme.colorScheme.White.copy(alpha = topBarColorTransition),
        label = "TopBarColorAnimation"
    )
    var showReviewSortBottomSheet by remember { mutableStateOf(false) }
    var initComposition by remember { mutableStateOf(true) }    // Is first composition?
    var expandOpeningHour by remember { mutableStateOf(false) }
    val rotateOpeningHourIconAngle by animateFloatAsState(
        targetValue = if (expandOpeningHour) -180f else 0f,
        label = "OpeningHourRotationAnimation"
    )

    LaunchedEffect(topBarColor) { Timber.e("topBarColor : $topBarColor") }
    LaunchedEffect(topBarColorTransition) { Timber.e("topBarColorTransition : $topBarColorTransition") }
    LaunchedEffect(tabState) {
        if (initComposition.not()) {
            listState.animateScrollToItem(index = TabsIndex)
        } else {
            initComposition = false
        }
    }

    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BakeRoadTheme.colorScheme.Gray50)
                .offset { IntOffset(x = 0, y = topPadding.toPx().toInt()) },    // Skip Composition. (start from Layout phrase)
            state = listState,
            contentPadding = PaddingValues(bottom = TopAppBarHeight)
        ) {
            if (state.loadingState.bakeryDetailLoading) {
                item { BakeryImageHeaderSkeleton() }
                item {
                    BakeryInfoSectionSkeleton(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth()
                            .background(BakeRoadTheme.colorScheme.White)
                    )
                }
            } else {
                item {
                    BakeryImageHeader(
                        modifier = Modifier.fillMaxWidth(),
                        imageList = state.bakeryImageList,
                        bakeryOpenStatus = state.bakeryInfo?.openStatus ?: BakeryOpenStatus.OPEN
                    )
                }
                item {
                    BakeryInfoSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        bakeryInfo = state.bakeryInfo,
                        reviewState = state.reviewState,
                        expandOpeningHour = expandOpeningHour,
                        rotateOpeningHourIconAngle = rotateOpeningHourIconAngle,
                        onExpandOpeningHourClick = { expandOpeningHour = !expandOpeningHour },
                        onWriteReviewClick = onWriteReviewClick
                    )
                }
            }
            stickyHeader {
                BakeRoadScrollableTabRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 4.dp,
                            ambientColor = BakeRoadTheme.colorScheme.Gray500,
                            spotColor = BakeRoadTheme.colorScheme.Gray500
                        ),
                    containerColor = BakeRoadTheme.colorScheme.White,
                    contentColor = BakeRoadTheme.colorScheme.Gray990,
                    edgePadding = 16.dp,
                    selectedTabIndex = BakeryDetailTab.entries.indexOf(tabState)
                ) {
                    BakeryDetailTab.entries.fastForEach { tab ->
                        BakeRoadTab(
                            selected = (tab == tabState),
                            onClick = { onTabSelect(tab) },
                            text = { Text(text = stringResource(id = tab.labelRes)) }
                        )
                    }
                }
            }
            when (tabState) {
                BakeryDetailTab.HOME -> {
                    home(
                        loadingState = state.loadingState,
                        reviewCount = state.reviewState.count,
                        menuList = state.menuList,
                        reviewList = state.reviewState.previewReviewList,
                        tourAreaList = state.tourAreaList,
                        localReviewLikeMap = state.reviewState.localLikeMap,
                        onViewAllMenuClick = { onTabSelect(BakeryDetailTab.MENU) },
                        onViewAllReviewClick = { onTabSelect(BakeryDetailTab.REVIEW) },
                        onViewAllTourAreaClick = { onTabSelect(BakeryDetailTab.TOUR_AREA) },
                        onReviewLikeClick = onReviewLikeClick
                    )
                }

                BakeryDetailTab.MENU -> {
                    menu(
                        loading = state.loadingState.bakeryDetailLoading,
                        menuList = state.menuList
                    )
                }

                BakeryDetailTab.REVIEW -> {
                    review(
                        state = state.reviewState,
                        tabState = reviewTabState,
                        sortState = reviewSortState,
                        myReviewPaging = myReviewPagingItems,
                        reviewPaging = reviewPagingItems,
                        onReviewTabSelect = onReviewTabSelect,
                        onSortClick = { showReviewSortBottomSheet = true },
                        onWriteReviewClick = onWriteReviewClick,
                        onReviewLikeClick = onReviewLikeClick
                    )
                }

                BakeryDetailTab.TOUR_AREA -> {
                    tourArea(
                        loading = state.loadingState.tourAreaLoading,
                        tourList = state.tourAreaList
                    )
                }
            }
        }

        BakeRoadTopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .drawBehind { drawRect(color = topBarColor) },  // Skip Composition and Layout. (start from Drawing phrase)
            containerColor = Color.Transparent,
            leftActions = {
                BakeRoadTopAppBarIcon(
                    iconRes = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_back,
                    contentDescription = "Back",
                    backgroundColor = BakeRoadTheme.colorScheme.White.copy(alpha = 0.6f),
                    onClick = onBackClick
                )
            },
            title = {
                if (topBarColorTransition == 1f) {
                    Text(text = state.bakeryInfo?.name.orEmpty())
                }
            },
            rightActions = {
                Row {
                    BakeRoadTopAppBarIcon(
                        iconRes = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_share,
                        contentDescription = "Share",
                        backgroundColor = BakeRoadTheme.colorScheme.White.copy(alpha = 0.6f),
                        onClick = {}
                    )
                    LikeIcon(
                        modifier = Modifier.padding(start = 12.dp),
                        size = 24.dp,
                        padding = 4.dp,
                        colors = LikeIconColors(
                            containerColor = BakeRoadTheme.colorScheme.White.copy(alpha = 0.6f),
                            likeIconContentColor = BakeRoadTheme.colorScheme.Error500,
                            unlikeIconContentColor = BakeRoadTheme.colorScheme.Black
                        ),
                        isLike = state.bakeryInfo?.isLike.orFalse(),
                        onClick = { result -> onBakeryLikeClick(result) }
                    )
                }
            }
        )

        if (showReviewSortBottomSheet) {
            ReviewSortBottomSheet(
                sort = reviewSortState,
                onDismissRequest = { showReviewSortBottomSheet = false },
                onSortSelect = { sort ->
                    if (sort != reviewSortState) onReviewSortSelect(sort)
                    showReviewSortBottomSheet = false
                },
                onCancel = { showReviewSortBottomSheet = false }
            )
        }
    }
}

@Preview
@Composable
private fun BakeryDetailScreenPreview() {
    BakeRoadTheme {
        val pagingData = PagingData.from(
            listOf(
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
                    menus = listOf("꿀고구마휘낭시에", "꿀고구마휘낭시에", "꿀고구마휘낭시에", "꿀고구마휘낭시에", "꿀고구마휘낭시에"),
                    photos = emptyList()
                )
            )
        )
        val lazyPagingItems = flowOf(pagingData).collectAsLazyPagingItems()

        BakeryDetailScreen(
            state = BakeryDetailState(),
            tabState = BakeryDetailTab.HOME,
            reviewTabState = ReviewTab.MY_REVIEW,
            reviewSortState = ReviewSortType.LIKE_COUNT_DESC,
            myReviewPagingItems = lazyPagingItems,
            reviewPagingItems = lazyPagingItems,
            onTabSelect = {},
            onReviewTabSelect = {},
            onReviewSortSelect = {},
            onWriteReviewClick = {},
            onBakeryLikeClick = {},
            onReviewLikeClick = { _, _ -> },
            onBackClick = {}
        )
    }
}