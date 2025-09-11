package com.twolskone.bakeroad.feature.onboard.preference

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.twolskone.bakeroad.core.analytics.LogComposeScreenEvent
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBarIcon
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.PreferenceOption
import com.twolskone.bakeroad.core.ui.PreferenceOptionListPage
import com.twolskone.bakeroad.core.ui.R
import com.twolskone.bakeroad.feature.onboard.preference.model.PreferenceOptionsState

@Composable
internal fun PreferenceOptionListScreen(
    modifier: Modifier = Modifier,
    state: PreferenceOptionsState,
    isEdit: Boolean,
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

        else -> return
    }

    LogComposeScreenEvent(screen = "PreferenceOptionListScreen")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.Start
    ) {
        BakeRoadTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            leftActions = {
                if (isEdit) {
                    BakeRoadTopAppBarIcon(
                        iconRes = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_back,
                        contentDescription = "Back",
                        onClick = { onPreviousPage(0) }
                    )
                }
            }
        )
        PreferenceOptionListPage(
            modifier = Modifier.fillMaxSize(),
            loading = state.loading,
            page = state.page,
            title = title,
            completionText = if (isEdit) stringResource(R.string.core_ui_button_save) else stringResource(R.string.core_ui_button_next),
            optionList = options,
            selectedOptions = selectedOptions,
            onOptionSelected = onOptionSelected,
            onPreviousPage = onPreviousPage,
            onNextPage = onNextPage,
            onComplete = onComplete
        )
    }
}