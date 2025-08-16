package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.skeleton.TourAreasSkeleton
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.core.ui.TourAreaCard
import kotlinx.collections.immutable.ImmutableList

/**
 * TourArea section
 */
internal fun LazyListScope.tourArea(
    loading: Boolean,
    tourList: ImmutableList<TourArea>
) {
    if (loading) {
        item {
            TourAreasSkeleton(
                modifier = Modifier
                    .background(color = BakeRoadTheme.colorScheme.White)
                    .padding(horizontal = 16.dp, vertical = 20.dp)
                    .fillMaxWidth()
            )
        }
    } else {
        items(
            items = tourList,
            key = { tourArea -> "${tourArea.title}_${tourArea.type}_${tourArea.mapX}_${tourArea.mapY}" }
        ) { tour ->
            TourAreaCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = BakeRoadTheme.colorScheme.White)
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                tourArea = tour
            )
        }
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(color = BakeRoadTheme.colorScheme.White)
            )
        }
    }
}