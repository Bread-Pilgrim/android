package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.TourArea

private val SimpleImageSize = 100.dp

// TODO. 공통 컴포넌트화 ?
@Composable
internal fun SimpleTourAreaCard(
    modifier: Modifier = Modifier,
    tourArea: TourArea
) {
    Column(modifier = modifier.width(SimpleImageSize)) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(SimpleImageSize),
                model = tourArea.imagePath,
                contentDescription = "TourArea",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_thumbnail)
            )
            BakeRoadChip(
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.TopStart),
                color = ChipColor.SUB,
                size = ChipSize.SMALL,
                selected = true,
                label = { Text(text = tourArea.type) }
            )
        }
        Text(
            modifier = Modifier.padding(top = 6.dp),
            text = tourArea.title,
            style = BakeRoadTheme.typography.bodySmallSemibold.copy(color = BakeRoadTheme.colorScheme.Gray990),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.padding(top = 3.dp),
            text = tourArea.address,
            style = BakeRoadTheme.typography.body2XsmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray400),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}