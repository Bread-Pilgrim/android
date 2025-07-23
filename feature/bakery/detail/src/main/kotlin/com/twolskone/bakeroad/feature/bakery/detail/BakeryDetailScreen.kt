package com.twolskone.bakeroad.feature.bakery.detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.util.fastForEach
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.twolskone.bakeroad.core.designsystem.component.check.CheckSize
import com.twolskone.bakeroad.core.designsystem.component.check.SingleCheck
import com.twolskone.bakeroad.core.designsystem.component.popup.BakeRoadSheet
import com.twolskone.bakeroad.core.designsystem.component.popup.PopupButton
import com.twolskone.bakeroad.core.designsystem.component.tab.BakeRoadScrollableTabRow
import com.twolskone.bakeroad.core.designsystem.component.tab.BakeRoadTab
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus
import com.twolskone.bakeroad.feature.bakery.detail.component.BakeryImageHeader
import com.twolskone.bakeroad.feature.bakery.detail.component.BakeryInfo
import com.twolskone.bakeroad.feature.bakery.detail.component.home
import com.twolskone.bakeroad.feature.bakery.detail.component.menu
import com.twolskone.bakeroad.feature.bakery.detail.component.review
import com.twolskone.bakeroad.feature.bakery.detail.component.tourArea
import com.twolskone.bakeroad.feature.bakery.detail.model.BakeryDetailTab
import com.twolskone.bakeroad.feature.bakery.detail.model.ReviewTab
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

private val TopAppBarHeight = 56.dp
private const val TabsIndex = 2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BakeryDetailScreen(
    modifier: Modifier = Modifier,
    state: BakeryDetailState,
    myReviewPaging: LazyPagingItems<BakeryReview>,
    reviewPaging: LazyPagingItems<BakeryReview>,
    onTabSelect: (BakeryDetailTab) -> Unit,
    onReviewTabSelect: (ReviewTab) -> Unit
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
    var showBottomSheet by remember { mutableStateOf(false) }
    var initComposition by remember { mutableStateOf(true) }    // Is first composition?
    var expandOpeningHour by remember { mutableStateOf(false) }
    val rotateOpeningHourIconAngle by animateFloatAsState(
        targetValue = if (expandOpeningHour) -180f else 0f,
        label = "OpeningHourRotationAnimation"
    )

    LaunchedEffect(topBarColor) {
        Timber.e("topBarColor : $topBarColor")
    }

    LaunchedEffect(topBarColorTransition) {
        Timber.e("topBarColorTransition : $topBarColorTransition")
    }

    LaunchedEffect(state.tab) {
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
            contentPadding = WindowInsets.navigationBars.asPaddingValues()
        ) {
            item(contentType = "bakeryImagePager") {
                BakeryImageHeader(
                    modifier = Modifier.fillMaxWidth(),
                    imageList = state.bakeryImageList,
                    bakeryOpenStatus = state.bakeryInfo?.openStatus ?: BakeryOpenStatus.OPEN
                )
            }
            item(contentType = "bakeryInfo") {
                BakeryInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    bakeryInfo = state.bakeryInfo,
                    expandOpeningHour = expandOpeningHour,
                    rotateOpeningHourIconAngle = rotateOpeningHourIconAngle,
                    onExpandOpeningHourClick = { expandOpeningHour = !expandOpeningHour }
                )
            }
            stickyHeader("tabs") {
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
                    selectedTabIndex = BakeryDetailTab.entries.indexOf(state.tab)
                ) {
                    BakeryDetailTab.entries.fastForEach { tab ->
                        BakeRoadTab(
                            selected = (tab == state.tab),
                            onClick = { onTabSelect(tab) },
                            text = { Text(text = stringResource(id = tab.labelId)) }
                        )
                    }
                }
            }
            when (state.tab) {
                BakeryDetailTab.HOME -> home(
                    reviewCount = state.reviewState.reviewCount,
                    menuList = state.menuList,
                    reviewList = state.reviewState.previewReviewList,
                    tourAreaList = state.tourAreaList
                )

                BakeryDetailTab.MENU -> menu(menuList = state.menuList)

                BakeryDetailTab.REVIEW -> review(
                    reviewState = state.reviewState,
                    myReviewPaging = myReviewPaging,
                    reviewPaging = reviewPaging,
                    onReviewTabSelect = onReviewTabSelect
                )

                BakeryDetailTab.TOUR_AREA -> tourArea(tourList = state.tourAreaList)
            }
        }
        BakeRoadTopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .drawBehind { drawRect(color = topBarColor) },  // Skip Composition and Layout. (start from Drawing phrase)
            containerColor = Color.Transparent,
            iconContentColor = BakeRoadTheme.colorScheme.Black,
            leftActions = {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = BakeRoadTheme.colorScheme.White.copy(alpha = 0.6f))
                        .singleClickable {}
                        .padding(6.dp),
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_back),
                        contentDescription = "Back"
                    )
                }
            },
            title = {
                if (topBarColorTransition == 1f) {
                    Timber.i("TopAppBar composition !!")
                    Text(text = state.bakeryInfo?.name.orEmpty())
                }
            },
            rightActions = {
                Row {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = BakeRoadTheme.colorScheme.White.copy(alpha = 0.6f))
                            .singleClickable {}
                            .padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_share),
                            contentDescription = "Back"
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .clip(CircleShape)
                            .background(color = BakeRoadTheme.colorScheme.White.copy(alpha = 0.6f))
                            .singleClickable {}
                            .padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_heart_stroke),
                            contentDescription = "Back"
                        )
                    }
                }
            }
        )
        if (showBottomSheet) {
            BakeRoadSheet(
                modifier = Modifier.fillMaxWidth(),
                buttonType = PopupButton.SHORT,
                title = stringResource(id = R.string.feature_bakery_detail_title_sort_order),
                primaryText = stringResource(id = R.string.feature_bakery_detail_button_sort),
                userActionContent = {
                    SingleCheck(
                        modifier = Modifier.fillMaxWidth(),
                        size = CheckSize.NORMAL,
                        selectedOption = "좋아요 순",
                        optionList = persistentListOf("좋아요 순", "최신 작성 순", "높은 평가 순", "낮은 평가 순"),
                        onCheck = {}
                    )
                },
                onDismissRequest = {
                    showBottomSheet = false
                },
                onPrimaryAction = {},
                onSecondaryAction = {}
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
            myReviewPaging = lazyPagingItems,
            reviewPaging = lazyPagingItems,
            onTabSelect = {},
            onReviewTabSelect = {}
        )
    }
}