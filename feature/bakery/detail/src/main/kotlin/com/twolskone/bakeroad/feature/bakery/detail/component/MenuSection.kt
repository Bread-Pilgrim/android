package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.feature.bakery.detail.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Menu section
 */
internal fun LazyListScope.menu(menuList: ImmutableList<BakeryDetail.Menu>) {
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
}

@Preview
@Composable
private fun MenuSectionPreview() {
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