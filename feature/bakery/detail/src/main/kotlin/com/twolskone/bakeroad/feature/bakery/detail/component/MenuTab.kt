package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.twolskone.bakeroad.core.common.kotlin.extension.toCommaString
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.feature.bakery.detail.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Menu tab.
 */
internal fun LazyListScope.menu(menuList: ImmutableList<BakeryDetail.Menu>) {
    item(contentType = "menuTitle") {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BakeRoadTheme.colorScheme.White)
                .padding(top = 20.dp, bottom = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.feature_bakery_detail_title_all_menu),
                style = BakeRoadTheme.typography.bodyLargeSemibold
            )
        }
    }
    itemsIndexed(
        items = menuList,
        contentType = { _, _ -> "menu" }
    ) { index, menu ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BakeRoadTheme.colorScheme.White)
        ) {
            Menu(modifier = Modifier.fillMaxWidth(), menu = menu)
            Spacer(modifier = Modifier.height(if (index == menuList.lastIndex) 20.dp else 16.dp))
        }
    }
}

/**
 * Menu item.
 */
@Composable
internal fun Menu(
    modifier: Modifier = Modifier,
    menu: BakeryDetail.Menu
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (menu.isSignature) {
                BakeRoadChip(
                    selected = false,
                    color = ChipColor.SUB,
                    size = ChipSize.SMALL,
                    label = { Text(text = stringResource(id = R.string.feature_bakery_detail_label_signature_menu)) }
                )
            }
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = menu.name,
                style = BakeRoadTheme.typography.bodySmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray900)
            )
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = stringResource(id = R.string.feature_bakery_detail_price, menu.price.toCommaString()),
                style = BakeRoadTheme.typography.bodySmallSemibold.copy(color = BakeRoadTheme.colorScheme.Gray990)
            )
        }
        if (menu.imageUrl.isNotBlank()) {
            AsyncImage(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .size(width = 100.dp, height = 80.dp),
                model = menu.imageUrl,
                contentDescription = "Menu",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_thumbnail)
            )
        }
    }
}

@Preview
@Composable
private fun MenuPreview() {
    BakeRoadTheme {
        Menu(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BakeRoadTheme.colorScheme.White),
            menu = BakeryDetail.Menu(
                name = "에그타르트",
                price = 3000,
                isSignature = true,
                imageUrl = ""
            )
        )
    }
}

@Preview
@Composable
private fun MenuTabPreview() {
    BakeRoadTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BakeRoadTheme.colorScheme.Gray40)
        ) {
            menu(
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