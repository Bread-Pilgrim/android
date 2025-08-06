package com.twolskone.bakeroad.feature.bakery.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.core.ui.BakeryCard
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun BakeryListScreen(
    modifier: Modifier = Modifier,
    bakeryType: BakeryType,
    pagingItems: LazyPagingItems<Bakery>,
    localLikeMap: PersistentMap<Int, Boolean>,
    onBakeryClick: (Bakery) -> Unit,
    onBakeryLikeClick: (Int, Boolean) -> Unit
) {
    val title = when (bakeryType) {
        BakeryType.PREFERENCE -> stringResource(id = R.string.feature_bakery_list_title_preference)
        BakeryType.HOT -> stringResource(id = R.string.feature_bakery_list_title_hot)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White)
            .systemBarsPadding()
    ) {
        BakeRoadTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            leftActions = {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .singleClickable {}
                        .padding(4.dp)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_back),
                        contentDescription = "Back"
                    )
                }
            },
            title = { Text(text = title) }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey { it.id }
            ) { index ->
                pagingItems[index]?.let { bakery ->
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
}

@Preview
@Composable
private fun BakeryListScreenPreview() {
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

        BakeryListScreen(
            bakeryType = BakeryType.PREFERENCE,
            pagingItems = lazyPagingItems,
            localLikeMap = persistentMapOf(),
            onBakeryClick = {},
            onBakeryLikeClick = { _, _ -> }
        )
    }
}
