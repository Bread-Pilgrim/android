package com.twolskone.bakeroad.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.feature.home.mvi.HomeIntent

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToBakeryList: (areaCode: String, BakeryType) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        state = state,
        onAreaSelect = { selected, code -> viewModel.intent(HomeIntent.SelectArea(selected = selected, areaCode = code)) },
        onSeeAllPreferenceBakeriesClick = {
            navigateToBakeryList(
                state.selectedAreaCodes.joinToString(separator = ","),
                BakeryType.PREFERENCE
            )
        },
        onTourCategorySelect = { selected, category -> viewModel.intent(HomeIntent.SelectTourAreaCategory(selected = selected, category = category)) }
    )
}