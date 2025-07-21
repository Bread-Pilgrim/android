package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.core.ui.TourAreaCard

/**
 * TourArea tab.
 */
internal fun LazyListScope.tourArea() {
    items(count = 10, contentType = { "tourArea" }) {

        TourAreaCard(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BakeRoadTheme.colorScheme.White)
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .padding(bottom = if (it == 9) 10.dp else 0.dp),
            tourArea = TourArea(
                title = "부평 깡통시장",
                address = "전통시장 / 광복동",
                type = "자연",
                imagePath = "",
                mapX = 0f,
                mapY = 0f
            )
        )
    }
}