package com.twolskone.bakeroad.feature.onboard.model

import androidx.annotation.IntRange
import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.model.PreferenceOption
import com.twolskone.bakeroad.core.model.PreferenceOptions
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList

@Immutable
internal data class PreferenceState(
    @IntRange(from = 1, to = 3) val page: Int = 1,
    val breadTypeList: ImmutableList<PreferenceOption> = persistentListOf(),
    val flavorList: ImmutableList<PreferenceOption> = persistentListOf(),
    val bakeryTypeList: ImmutableList<PreferenceOption> = persistentListOf(),
    val selectedBreadTypes: ImmutableSet<Int> = persistentSetOf(1),
    val selectedFlavors: ImmutableSet<Int> = persistentSetOf(10),
    val selectedBakeryTypes: ImmutableSet<Int> = persistentSetOf(20)
)

internal fun PreferenceOptions.toUiState(): PreferenceState =
    PreferenceState(
        breadTypeList = breadTypes.toImmutableList(),
        flavorList = flavors.toImmutableList(),
        bakeryTypeList = (atmospheres + commercialAreas).toImmutableList(),
    )