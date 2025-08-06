package com.twolskone.bakeroad.feature.mybakery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadTextButton
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.tab.BakeRoadScrollableTabRow
import com.twolskone.bakeroad.core.designsystem.component.tab.BakeRoadTab
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.type.BakerySortType
import com.twolskone.bakeroad.core.ui.BakeryCard
import com.twolskone.bakeroad.core.ui.model.PagingUiState
import com.twolskone.bakeroad.feature.mybakery.component.BakerySortBottomSheet
import com.twolskone.bakeroad.feature.mybakery.component.ViewModeToggleButton
import com.twolskone.bakeroad.feature.mybakery.component.label
import com.twolskone.bakeroad.feature.mybakery.model.Tab
import com.twolskone.bakeroad.feature.mybakery.model.ViewMode
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakeryState
import kotlinx.collections.immutable.persistentMapOf

private val TabEdgePadding = 16.dp
private val TabMinWidth = 120.dp

@Composable
internal fun MyBakeryScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    state: MyBakeryState,
    visitedListState: LazyListState,
    likeListState: LazyListState,
    onTabSelect: (Tab) -> Unit,
    onSortSelect: (Tab, BakerySortType) -> Unit,
    onBakeryClick: (Bakery) -> Unit,
    onBakeryLikeClick: (Int, Boolean) -> Unit
) {
    val density = LocalDensity.current
    var showSortBottomSheet by remember { mutableStateOf(false) }

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
                selectedTabIndex = Tab.entries.indexOf(state.tab)
            ) {
                Tab.entries.fastForEach { tab ->
                    BakeRoadTab(
                        modifier = Modifier.width(tabWidth),
                        selected = tab == state.tab,
                        onClick = { onTabSelect(tab) },
                        text = { Text(text = stringResource(id = tab.labelRes)) }
                    )
                }
            }
        }
        when (state.tab) {
            Tab.VISITED -> MyBakerySection(
                sort = state.visitedSection.sort,
                paging = state.visitedSection.paging,
                listState = visitedListState,
                onSortClick = { showSortBottomSheet = true }
            )

            Tab.LIKE -> MyBakerySection(
                sort = state.likeSection.sort,
                paging = state.likeSection.paging,
                listState = likeListState,
                onSortClick = { showSortBottomSheet = true }
            )
        }
    }
    if (showSortBottomSheet) {
        BakerySortBottomSheet(
            sort = state.currentSort,
            onDismissRequest = { showSortBottomSheet = false },
            onSortSelect = { sort ->
                if (sort != state.currentSort) onSortSelect(state.tab, sort)
                showSortBottomSheet = false
            },
            onCancel = { showSortBottomSheet = false }
        )
    }
}

@Composable
private fun ColumnScope.MyBakerySection(
    sort: BakerySortType,
    paging: PagingUiState<Bakery>,
    listState: LazyListState,
    onSortClick: () -> Unit
) {
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
            selectedViewMode = ViewMode.LIST,
            onClick = { }
        )
        BakeRoadTextButton(
            style = TextButtonStyle.ASSISTIVE,
            size = TextButtonSize.SMALL,
            onClick = {},
            content = { Text(text = sort.label) }
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState
    ) {
        items(
            items = paging.list,
//            key = { bakery -> bakery.id }
        ) { bakery ->
            BakeryCard(
                bakery = bakery,
                likeMap = persistentMapOf(),
                onCardClick = { },
                onLikeClick = { _, _ -> }
            )
        }
    }
}

@Preview
@Composable
private fun MyBakeryScreenPreview() {
    BakeRoadTheme {
        MyBakeryScreen(
            modifier = Modifier.fillMaxSize(),
            padding = PaddingValues(),
            state = MyBakeryState(),
            visitedListState = rememberLazyListState(),
            likeListState = rememberLazyListState(),
            onTabSelect = {},
            onSortSelect = { _, _ -> },
            onBakeryClick = {},
            onBakeryLikeClick = { _, _ -> }
        )
    }
}