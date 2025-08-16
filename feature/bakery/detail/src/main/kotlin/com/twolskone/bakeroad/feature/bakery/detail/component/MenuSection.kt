package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.skeleton.TitleSkeleton
import com.twolskone.bakeroad.core.designsystem.extension.shimmerEffect
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.feature.bakery.detail.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Menu section
 */
internal fun LazyListScope.menu(
    loading: Boolean,
    menuList: ImmutableList<BakeryDetail.Menu>
) {
    if (loading) {
        item { MenuSectionSkeleton() }
    } else {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = BakeRoadTheme.colorScheme.White)
                    .padding(horizontal = 16.dp)
                    .padding(top = 20.dp, bottom = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.feature_bakery_detail_title_all_menu),
                    style = BakeRoadTheme.typography.bodyLargeSemibold
                )
            }
        }
        itemsIndexed(items = menuList) { index, menu ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = BakeRoadTheme.colorScheme.White)
                    .padding(horizontal = 16.dp)
            ) {
                MenuListItem(modifier = Modifier.fillMaxWidth(), menu = menu)
                Spacer(modifier = Modifier.height(if (index == menuList.lastIndex) 20.dp else 16.dp))
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillParentMaxHeight(fraction = 0.8f)
                    .background(color = BakeRoadTheme.colorScheme.White)
            )
        }
    }
}

@Composable
private fun MenuSectionSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BakeRoadTheme.colorScheme.White)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleSkeleton(
            modifier = Modifier.fillMaxWidth(),
            titleWidth = 154.dp
        )
        repeat(5) { index ->
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
    }
}

@Preview
@Composable
private fun MenuSectionPreview() {
    BakeRoadTheme {
        val loading = true
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BakeRoadTheme.colorScheme.Gray40)
        ) {
            menu(
                loading = loading,
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
                )
            )
        }
    }
}