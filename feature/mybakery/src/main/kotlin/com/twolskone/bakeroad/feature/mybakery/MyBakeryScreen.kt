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
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus
import com.twolskone.bakeroad.core.ui.BakeryCard
import com.twolskone.bakeroad.feature.mybakery.component.ViewModeToggleButton
import com.twolskone.bakeroad.feature.mybakery.model.Tab
import com.twolskone.bakeroad.feature.mybakery.model.ViewMode
import kotlinx.collections.immutable.persistentMapOf

private val TabEdgePadding = 16.dp
private val TabMinWidth = 120.dp

@Composable
internal fun MyBakeryScreen(
    modifier: Modifier = Modifier,
    tabState: Tab,
//    padding: PaddingValues
) {
    val density = LocalDensity.current
    var visitedViewMode by remember { mutableStateOf(ViewMode.LIST) }
    var likeViewMode by remember { mutableStateOf(ViewMode.LIST) }
    val currentViewMode = when (tabState) {
        Tab.VISITED -> visitedViewMode
        Tab.LIKE -> likeViewMode
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White)
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
                        onClick = { },
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
                content = { Text(text = "최근 저장 순") }
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(count = 10) {
                BakeryCard(
                    bakery = Bakery(
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
                    ),
                    likeMap = persistentMapOf(),
                    onCardClick = {},
                    onLikeClick = { _, _ -> }
                )
            }
        }
    }
}

@Preview
@Composable
private fun MyBakeryScreenPreview() {
    BakeRoadTheme {
        MyBakeryScreen(
            modifier = Modifier.fillMaxSize(),
            tabState = Tab.VISITED
        )
    }
}