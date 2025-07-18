package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.bakery.detail.R

@Composable
fun Menu(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            BakeRoadChip(
                selected = false,
                color = ChipColor.SUB,
                size = ChipSize.SMALL,
                label = { Text(text = stringResource(id = R.string.feature_bakery_detail_label_signature_menu)) }
            )
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = "에그타르트",
                style = BakeRoadTheme.typography.bodySmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray900)
            )
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = "3,000원",
                style = BakeRoadTheme.typography.bodySmallSemibold.copy(color = BakeRoadTheme.colorScheme.Gray990)
            )
        }
        AsyncImage(
            modifier = Modifier
                .padding(start = 12.dp)
                .clip(RoundedCornerShape(8.dp))
                .size(width = 100.dp, height = 80.dp),
            model = "",
            contentDescription = "Menu",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_thumbnail)
        )
    }
}

@Preview
@Composable
private fun MenuPreview() {
    BakeRoadTheme {
        Menu(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BakeRoadTheme.colorScheme.White)
        )
    }
}