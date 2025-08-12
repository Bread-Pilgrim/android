package com.twolskone.bakeroad.feature.report.component

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.type.DayOfWeek
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

/**
 * 요일별 빵 섭취 패턴 카드
 */
@Composable
internal fun WeeklyBreadConsumptionCard(
    modifier: Modifier = Modifier,
    data: ImmutableList<Pair<DayOfWeek, Int>>
) {
    Card(
        modifier = modifier,
        shape = CardShape,
        colors = CardColors(
            containerColor = BakeRoadTheme.colorScheme.White,
            contentColor = BakeRoadTheme.colorScheme.Gray800,
            disabledContainerColor = BakeRoadTheme.colorScheme.Gray100,
            disabledContentColor = BakeRoadTheme.colorScheme.Gray500
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {

        }
    }
}

private val BarMaxHeight = 80.dp

@Composable
private fun WeeklyBreadConsumptionBar(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, to = 1.0) heightRadio: Float
) {

}

@Preview
@Composable
private fun WeeklyBreadConsumptionCardPreview() {
    BakeRoadTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BakeRoadTheme.colorScheme.White)
        ) {
            WeeklyBreadConsumptionCard(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                data = DayOfWeek.entries.map {
                    it to (0..3).random()
                }.toImmutableList()
            )
        }
    }
}