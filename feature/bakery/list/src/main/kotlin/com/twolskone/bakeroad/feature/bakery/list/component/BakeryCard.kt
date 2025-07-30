package com.twolskone.bakeroad.feature.bakery.list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFilter
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastJoinToString
import coil.compose.AsyncImage
import com.twolskone.bakeroad.core.common.kotlin.extension.toCommaString
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipStyle
import com.twolskone.bakeroad.core.designsystem.extension.noRippleSingleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus
import com.twolskone.bakeroad.core.ui.BakeryOpenStatusChip
import com.twolskone.bakeroad.core.ui.LikeIcon
import com.twolskone.bakeroad.feature.bakery.list.R
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf

private val ImageWidth = 148.dp
private val ImageShape = RoundedCornerShape(9.dp)

/**
 * 빵집 카드
 */
@Composable
internal fun BakeryCard(
    modifier: Modifier = Modifier,
    bakery: Bakery,
    likeMap: PersistentMap<Int, Boolean>,
    onCardClick: (Bakery) -> Unit,
    onLikeClick: (Int, Boolean) -> Unit
) {
    val isLike = likeMap[bakery.id] ?: bakery.isLike

    Row(modifier = modifier.noRippleSingleClickable { onCardClick(bakery) }) {
        Box(
            modifier = Modifier
                .width(ImageWidth)
                .aspectRatio(4f / 3f)
                .clip(ImageShape)
                .background(color = BakeRoadTheme.colorScheme.Gray50, shape = ImageShape)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = bakery.imageUrl,
                contentDescription = "Bakery",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_thumbnail)
            )
            LikeIcon(
                modifier = Modifier.align(Alignment.TopEnd),
                size = 20.dp,
                padding = 8.dp,
                isLike = isLike,
                onClick = { result -> onLikeClick(bakery.id, result) }
            )
            BakeryOpenStatusChip(
                modifier = Modifier
                    .padding(6.dp)
                    .align(Alignment.BottomStart),
                size = ChipSize.SMALL,
                style = ChipStyle.FILL,
                openStatus = bakery.openStatus
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 12.dp, top = 2.dp)
                .weight(1f)
        ) {
            Text(
                modifier = Modifier,
                text = bakery.name,
                style = BakeRoadTheme.typography.bodyMediumSemibold.copy(color = BakeRoadTheme.colorScheme.Black)
            )
            Row(modifier = Modifier.padding(top = 6.dp)) {
                Image(
                    imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_star_yellow),
                    contentDescription = "RatingStar"
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = bakery.rating.toString(),
                    style = BakeRoadTheme.typography.body2XsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray950)
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(id = R.string.feature_bakery_list_label_review_count, bakery.reviewCount.toCommaString()),
                    style = BakeRoadTheme.typography.body2XsmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray400)
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_location),
                    contentDescription = "Location"
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = listOf(bakery.addressGu, bakery.addressDong)
                        .fastFilter { it.isNotBlank() }
                        .fastJoinToString(separator = ", "),
                    style = BakeRoadTheme.typography.body2XsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray950)
                )
            }
            FlowRow(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth(),
                maxLines = 2,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                bakery.signatureMenus.take(3).fastForEach { menu ->
                    BakeRoadChip(
                        size = ChipSize.SMALL,
                        color = ChipColor.LIGHT_GRAY,
                        selected = false,
                        label = {
                            Text(
                                text = menu,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BakeryCardPreview() {
    BakeRoadTheme {
        BakeryCard(
            modifier = Modifier.fillMaxWidth(),
            likeMap = persistentMapOf(1 to true),
            bakery = Bakery(
                id = 1,
                name = "서라당",
                areaCode = 14,
                rating = 4.7f,
                reviewCount = 20203,
                openStatus = BakeryOpenStatus.CLOSED,
                imageUrl = "",
                addressGu = "관악구",
                addressDong = "",
                isLike = true,
                signatureMenus = listOf("소금빵", "올리브치 치아바타", "겁나긴메뉴겁나긴메뉴겁나긴메뉴겁나긴메뉴겁나긴메뉴겁나긴메뉴겁나긴메뉴겁나긴메뉴겁나긴메뉴")
            ),
            onCardClick = {},
            onLikeClick = { _, _ -> }
        )
    }
}