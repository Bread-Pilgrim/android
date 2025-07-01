package com.twolskone.bakeroad.feature.onboard.preference

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twolskone.bakeroad.core.model.BreadPreference
import com.twolskone.bakeroad.core.ui.BreadPreferencePage
import com.twolskone.bakeroad.feature.onboard.mvi.PreferenceState

@Composable
internal fun PreferenceScreen(
    modifier: Modifier = Modifier,
    state: PreferenceState,
    onPreferenceSelected: (BreadPreference) -> Unit,
    onPreviousPage: (Int) -> Unit,
    onNextPage: (Int) -> Unit,
    onComplete: () -> Unit
) {
    val (preferences, selectedPreferences) = when (state.page) {
        1 -> state.breadPreferenceList to state.selectedBreadPreferences
        2 -> state.breadTasteList to state.selectedBreadTastes
        else -> state.bakeryPreferenceList to state.selectedBakeryPreferences
    }

    BreadPreferencePage(
        modifier = modifier,
        page = state.page,
        breadPreferences = preferences,
        selectedBreadPreferences = selectedPreferences,
        onPreferenceSelected = onPreferenceSelected,
        onPreviousPage = onPreviousPage,
        onNextPage = onNextPage,
        onComplete = onComplete
    )
}

