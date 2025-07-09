package com.twolskone.bakeroad.feature.bakery.list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

private val ImageWidth = 148.dp
private val ImageShape = RoundedCornerShape(9.dp)

/**
 * 빵집 카드
 */
@Composable
internal fun BakeryCard(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(ImageWidth)
                .aspectRatio(4f / 3f)
                .clip(ImageShape)
                .background(color = BakeRoadTheme.colorScheme.Gray50, shape = ImageShape)
        ) {
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd),
                imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_heart_stroke),
                contentDescription = "Bookmark"
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = "서라당",
                style = BakeRoadTheme.typography.bodyMediumSemibold.copy(color = BakeRoadTheme.colorScheme.Black)
            )
            Row(modifier = Modifier.padding(top = 6.dp)) {
                Image(
                    imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_star_yellow),
                    contentDescription = "RatingStar"
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "4.7",
                    style = BakeRoadTheme.typography.body2XsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray950)
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "(리뷰 20,203)",
                    style = BakeRoadTheme.typography.body2XsmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray400)
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BakeRoadChip(
                    size = ChipSize.SMALL,
                    color = ChipColor.MAIN,
                    selected = true,
                    label = { Text(text = "영업중") }
                )
                Image(
                    modifier = Modifier.padding(start = 6.dp),
                    imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_location),
                    contentDescription = "Location"
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "관악구, 신대방역",
                    style = BakeRoadTheme.typography.body2XsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray950)
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                BakeRoadChip(
                    size = ChipSize.SMALL,
                    color = ChipColor.MAIN,
                    selected = false,
                    label = { Text(text = "마카롱") }
                )
                BakeRoadChip(
                    size = ChipSize.SMALL,
                    color = ChipColor.MAIN,
                    selected = false,
                    label = { Text(text = "타르트") }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BakeryCardPreview() {
    BakeRoadTheme {
        BakeryCard(modifier = Modifier.fillMaxWidth())
    }
}