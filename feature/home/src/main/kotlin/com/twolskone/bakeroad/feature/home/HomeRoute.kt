package com.twolskone.bakeroad.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.feature.home.mvi.HomeIntent

@Composable
internal fun HomeRoute(
    padding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToBakeryList: (areaCode: String, BakeryType) -> Unit,
    navigateToBakeryDetail: (bakeryId: Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        padding = padding,
        state = state,
        onAreaSelect = { selected, code -> viewModel.intent(HomeIntent.SelectArea(selected = selected, areaCode = code)) },
        onTourCategorySelect = { selected, category -> viewModel.intent(HomeIntent.SelectTourAreaCategory(selected = selected, category = category)) },
        onSeeAllBakeriesClick = { bakeryType ->
            navigateToBakeryList(
                state.selectedAreaCodes.joinToString(separator = ","),
                bakeryType
            )
        },
        onBakeryClick = { bakery -> navigateToBakeryDetail(bakery.id) }
    )
}