package com.twolskone.bakeroad.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.twolskone.bakeroad.core.common.kotlin.extension.toCommaString
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipStyle
import com.twolskone.bakeroad.core.designsystem.extension.noRippleSingleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.RecommendBakery
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus

private val ImageSize = 116.dp
private val ImageShape = RoundedCornerShape(6.dp)
private val CardHeight = 213.dp

/**
 * 빵집 추천 카드
 */
@Composable
fun RecommendBakeryCard(
    modifier: Modifier = Modifier,
    bakery: RecommendBakery,
    onCardClick: (RecommendBakery) -> Unit
) {
    Column(
        modifier = modifier
            .width(ImageSize)
            .height(CardHeight)
            .noRippleSingleClickable { onCardClick(bakery) }
    ) {
        Box(
            modifier = Modifier
                .size(ImageSize)
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
        }
        Text(
            modifier = Modifier
                .weight(1f, fill = false)
                .padding(top = 6.dp),
            text = bakery.name,
            style = BakeRoadTheme.typography.bodyXsmallSemibold.copy(color = BakeRoadTheme.colorScheme.Gray990),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            modifier = Modifier.padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.core_ui_ic_star_yellow),
                contentDescription = "RatingStar"
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = bakery.rating.toString(),
                style = BakeRoadTheme.typography.body2XsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray950)
            )
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = "(${bakery.reviewCount.toCommaString()})",
                style = BakeRoadTheme.typography.body2XsmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray400)
            )
        }
        BakeryOpenStatusChip(
            modifier = Modifier.padding(top = 6.dp),
            size = ChipSize.SMALL,
            style = ChipStyle.WEAK,
            openStatus = bakery.openStatus
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun BakeryCardPreview() {
    BakeRoadTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BakeRoadTheme.colorScheme.White),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            RecommendBakeryCard(
                bakery = RecommendBakery(
                    id = 1,
                    name = "서라당",
                    areaCode = 14,
                    rating = 4.7f,
                    reviewCount = 10,
                    openStatus = BakeryOpenStatus.OPEN,
                    imageUrl = "",
                    isLike = false
                ),
                onCardClick = {}
            )
            RecommendBakeryCard(
                bakery = RecommendBakery(
                    id = 1,
                    name = "런던 베이글 뮤지엄런던 베이글 뮤지엄",
                    areaCode = 14,
                    rating = 4.7f,
                    reviewCount = 10,
                    openStatus = BakeryOpenStatus.CLOSED,
                    imageUrl = "",
                    isLike = true
                ),
                onCardClick = {},
            )
        }
    }
}