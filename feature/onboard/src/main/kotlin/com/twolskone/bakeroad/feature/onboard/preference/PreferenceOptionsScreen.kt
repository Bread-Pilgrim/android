package com.twolskone.bakeroad.feature.onboard.preference

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.twolskone.bakeroad.core.model.PreferenceOption
import com.twolskone.bakeroad.core.ui.PreferenceOptionsPage
import com.twolskone.bakeroad.core.ui.R
import com.twolskone.bakeroad.feature.onboard.model.PreferenceOptionsState

@Composable
internal fun PreferenceOptionsScreen(
    modifier: Modifier = Modifier,
    state: PreferenceOptionsState,
    onOptionSelected: (Boolean, PreferenceOption) -> Unit,
    onPreviousPage: (Int) -> Unit,
    onNextPage: (Int) -> Unit,
    onComplete: () -> Unit
) {
    val (title, options, selectedOptions) = when (state.page) {
        1 -> Triple(
            stringResource(R.string.core_ui_title_preference_bread_type),
            state.breadTypeList,
            state.selectedBreadTypes
        )

        2 -> Triple(
            stringResource(R.string.core_ui_title_preference_flavor),
            state.flavorList,
            state.selectedFlavors
        )

        3 -> Triple(
            stringResource(R.string.core_ui_title_preference_bakery_type),
            state.bakeryTypeList,
            state.selectedBakeryTypes
        )

        4 -> Triple(
            stringResource(R.string.core_ui_title_preference_commercial_area),
            state.commercialAreaList,
            state.selectedCommercialAreas
        )

        else -> return
    }

    PreferenceOptionsPage(
        modifier = modifier,
        page = state.page,
        title = title,
        optionList = options,
        selectedOptions = selectedOptions,
        onOptionSelected = onOptionSelected,
        onPreviousPage = onPreviousPage,
        onNextPage = onNextPage,
        onComplete = onComplete
    )
}

