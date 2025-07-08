package com.twolskone.bakeroad.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

private val ImageShape = RoundedCornerShape(10.dp)

/**
 * 관광지 추천 카드
 */
@Composable
internal fun AttractionCard(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(21f / 9f)
                .clip(ImageShape)
                .background(color = BakeRoadTheme.colorScheme.Gray50, shape = ImageShape)
        )
        Text(
            modifier = Modifier.padding(top = 6.dp),
            text = "부평 깡통시장",
            style = BakeRoadTheme.typography.bodyMediumSemibold.copy(color = BakeRoadTheme.colorScheme.Gray990)
        )
        Row(
            modifier = Modifier.padding(top = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = "전통시장 / 광복동",
                style = BakeRoadTheme.typography.body2XsmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray400)
            )
            BakeRoadChip(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .padding(start = 6.dp),
                size = ChipSize.SMALL,
                color = ChipColor.MAIN,
                selected = true,
                label = { Text(text = "매일 10:00 ~ 20:00") }
            )
        }
    }
}

@Preview
@Composable
private fun AttractionCardPreview() {
    BakeRoadTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BakeRoadTheme.colorScheme.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AttractionCard(modifier = Modifier.fillMaxWidth())
            AttractionCard(modifier = Modifier.fillMaxWidth())
        }
    }
}