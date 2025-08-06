package com.twolskone.bakeroad.feature.mybakery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadTextButton
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.tab.BakeRoadScrollableTabRow
import com.twolskone.bakeroad.core.designsystem.component.tab.BakeRoadTab
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus
import com.twolskone.bakeroad.core.model.type.BakerySortType
import com.twolskone.bakeroad.core.ui.BakeryCard
import com.twolskone.bakeroad.feature.mybakery.component.BakerySortBottomSheet
import com.twolskone.bakeroad.feature.mybakery.component.ViewModeToggleButton
import com.twolskone.bakeroad.feature.mybakery.component.label
import com.twolskone.bakeroad.feature.mybakery.model.Tab
import com.twolskone.bakeroad.feature.mybakery.model.ViewMode
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.flow.flowOf

private val TabEdgePadding = 16.dp
private val TabMinWidth = 120.dp

@Composable
internal fun MyBakeryScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    tabState: Tab,
    sortState: BakerySortType,
    bakeryPagingItems: LazyPagingItems<Bakery>,
    localLikeMap: PersistentMap<Int, Boolean>,
    onTabSelect: (Tab) -> Unit,
    onSortSelect: (BakerySortType) -> Unit,
    onBakeryClick: (Bakery) -> Unit,
    onBakeryLikeClick: (Int, Boolean) -> Unit
) {
    val density = LocalDensity.current
    var visitedViewMode by remember { mutableStateOf(ViewMode.LIST) }
    var likeViewMode by remember { mutableStateOf(ViewMode.LIST) }
    var currentViewMode = when (tabState) {
        Tab.VISITED -> visitedViewMode
        Tab.LIKE -> likeViewMode
    }
    var visitedSortType by remember { mutableStateOf(BakerySortType.CREATED_AT_DESC) }
    var likeSortType by remember { mutableStateOf(BakerySortType.CREATED_AT_DESC) }
    var currentSortType = when (tabState) {
        Tab.VISITED -> visitedSortType
        Tab.LIKE -> likeSortType
    }
    var showSortBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(sortState) {
        currentSortType = sortState
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White)
            .padding(bottom = padding.calculateBottomPadding())
    ) {
        BakeRoadTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = { Text(text = stringResource(id = R.string.feature_mybakery)) }
        )
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val parentWidth = with(density) { constraints.maxWidth.toDp() }
            val tabWidth = (parentWidth / 2 - TabEdgePadding).coerceAtLeast(TabMinWidth)
            BakeRoadScrollableTabRow(
                modifier = Modifier.fillMaxWidth(),
                containerColor = BakeRoadTheme.colorScheme.White,
                contentColor = BakeRoadTheme.colorScheme.Gray990,
                edgePadding = TabEdgePadding,
                selectedTabIndex = Tab.entries.indexOf(tabState)
            ) {
                Tab.entries.fastForEach { tab ->
                    BakeRoadTab(
                        modifier = Modifier.width(tabWidth),
                        selected = tab == tabState,
                        onClick = { onTabSelect(tab) },
                        text = { Text(text = stringResource(id = tab.labelRes)) }
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ViewModeToggleButton(
                modifier = Modifier.padding(start = 6.dp),
                selectedViewMode = currentViewMode,
                onClick = { viewMode ->
                    when (tabState) {
                        Tab.VISITED -> visitedViewMode = viewMode
                        Tab.LIKE -> likeViewMode = viewMode
                    }
                }
            )
            BakeRoadTextButton(
                style = TextButtonStyle.ASSISTIVE,
                size = TextButtonSize.SMALL,
                onClick = {},
                content = { Text(text = currentSortType.label) }
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                count = bakeryPagingItems.itemCount,
                key = { index -> bakeryPagingItems.peek(index)?.id ?: "placeholder_$index" }
            ) { index ->
                bakeryPagingItems[index]
                    ?.let { bakery ->
                        BakeryCard(
                            bakery = bakery,
                            likeMap = localLikeMap,
                            onCardClick = onBakeryClick,
                            onLikeClick = onBakeryLikeClick
                        )
                    }
            }
        }
    }
    if (showSortBottomSheet) {
        BakerySortBottomSheet(
            sort = currentSortType,
            onDismissRequest = { showSortBottomSheet = false },
            onSortSelect = { sort ->
                if (sort != currentSortType) onSortSelect(sort)
                showSortBottomSheet = false
            },
            onCancel = { showSortBottomSheet = false }
        )
    }
}

@Preview
@Composable
private fun MyBakeryScreenPreview() {
    BakeRoadTheme {
        val pagingData = PagingData.from(
            listOf(
                Bakery(
                    id = 1,
                    name = "서라당",
                    areaCode = 14,
                    rating = 4.7f,
                    reviewCount = 20203,
                    openStatus = BakeryOpenStatus.OPEN,
                    imageUrl = "",
                    addressGu = "",
                    addressDong = "",
                    isLike = true,
                    signatureMenus = emptyList()
                )
            )
        )
        val lazyPagingItems = flowOf(pagingData).collectAsLazyPagingItems()

        MyBakeryScreen(
            modifier = Modifier.fillMaxSize(),
            padding = PaddingValues(),
            tabState = Tab.VISITED,
            sortState = BakerySortType.CREATED_AT_DESC,
            bakeryPagingItems = lazyPagingItems,
            localLikeMap = persistentMapOf(1 to true),
            onTabSelect = {},
            onSortSelect = {},
            onBakeryClick = {},
            onBakeryLikeClick = { _, _ -> }
        )
    }
}