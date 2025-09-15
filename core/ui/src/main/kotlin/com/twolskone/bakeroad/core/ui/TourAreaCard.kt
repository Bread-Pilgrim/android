package com.twolskone.bakeroad.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.extension.noRippleSingleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.TourArea

private val ImageShape = RoundedCornerShape(10.dp)

/**
 * 주변 관광지 카드
 */
@Composable
fun TourAreaCard(
    modifier: Modifier = Modifier,
    tourArea: TourArea,
    onClick: (TourArea) -> Unit
) {
    Column(modifier = modifier.noRippleSingleClickable { onClick(tourArea) }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(ImageShape)
                .background(color = BakeRoadTheme.colorScheme.Gray50, shape = ImageShape)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = tourArea.imagePath,
                contentDescription = "TourArea",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_thumbnail)
            )
        }
        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BakeRoadChip(
                size = ChipSize.SMALL,
                color = ChipColor.MAIN,
                selected = false,
                label = { Text(text = tourArea.type) }
            )
            Text(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .weight(1f, fill = false),
                text = tourArea.title,
                style = BakeRoadTheme.typography.bodyMediumSemibold.copy(color = BakeRoadTheme.colorScheme.Gray990)
            )
        }
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = tourArea.address,
            style = BakeRoadTheme.typography.body2XsmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray400)
        )
    }
}

@Preview
@Composable
private fun TourAreaCardPreview() {
    BakeRoadTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BakeRoadTheme.colorScheme.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TourAreaCard(
                modifier = Modifier.fillMaxWidth(),
                tourArea = TourArea(
                    title = "부평 깡통시장",
                    address = "전통시장 / 광복동",
                    type = "자연",
                    imagePath = "",
                    latitude = 0f,
                    longitude = 0f
                ),
                onClick = {}
            )
            TourAreaCard(
                modifier = Modifier.fillMaxWidth(),
                tourArea = TourArea(
                    title = "부평 깡통시장",
                    address = "전통시장 / 광복동",
                    type = "자연",
                    imagePath = "",
                    latitude = 0f,
                    longitude = 0f
                ),
                onClick = {}
            )
        }
    }
}