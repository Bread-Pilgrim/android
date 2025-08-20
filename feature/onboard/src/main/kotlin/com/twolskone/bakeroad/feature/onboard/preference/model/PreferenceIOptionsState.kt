package com.twolskone.bakeroad.feature.onboard.preference.model

import androidx.annotation.IntRange
import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.model.PreferenceOption
import com.twolskone.bakeroad.core.model.PreferenceOptionIds
import com.twolskone.bakeroad.core.model.PreferenceOptions
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList

@Immutable
internal data class PreferenceOptionsState(
    val loading: Boolean = true,
    @IntRange(from = 1, to = 3) val page: Int = 1,
    val breadTypeList: ImmutableList<PreferenceOption> = persistentListOf(),
    val flavorList: ImmutableList<PreferenceOption> = persistentListOf(),
    val bakeryTypeList: ImmutableList<PreferenceOption> = persistentListOf(),
    val selectedBreadTypes: PersistentSet<Int> = persistentSetOf(),
    val selectedFlavors: PersistentSet<Int> = persistentSetOf(),
    val selectedBakeryTypes: PersistentSet<Int> = persistentSetOf(),
    val originSelectedTypes: PreferenceOptionIds = PreferenceOptionIds( // 취향 변경 시, 기존에 선택한 취향 옵션 ID 목록
        flavors = emptyList(),
        breadTypes = emptyList(),
        atmospheres = emptyList()
    )
) {

    // 취향 변경 시, 추가된 취향 목록
    val addedList: List<Int>
        get() = buildList {
            selectedBreadTypes.forEach { id ->
                if (id !in originSelectedTypes.breadTypes) {
                    add(id)
                }
            }
            selectedFlavors.forEach { id ->
                if (id !in originSelectedTypes.flavors) {
                    add(id)
                }
            }
            selectedBakeryTypes.forEach { id ->
                if (id !in originSelectedTypes.atmospheres) {
                    add(id)
                }
            }
        }

    // 취향 변경 시, 삭제된 취향 목록
    val deletedList: List<Int>
        get() = buildList {
            originSelectedTypes.breadTypes.forEach { id ->
                if (id !in selectedBreadTypes) {
                    add(id)
                }
            }
            originSelectedTypes.flavors.forEach { id ->
                if (id !in selectedFlavors) {
                    add(id)
                }
            }
            originSelectedTypes.atmospheres.forEach { id ->
                if (id !in selectedBakeryTypes) {
                    add(id)
                }
            }
        }
}

internal fun PreferenceOptionsState.copy(
    loading: Boolean,
    preferenceOptions: PreferenceOptions
): PreferenceOptionsState =
    copy(
        loading = loading,
        breadTypeList = preferenceOptions.breadTypes.toImmutableList(),
        flavorList = preferenceOptions.flavors.toImmutableList(),
        bakeryTypeList = preferenceOptions.atmospheres.toImmutableList()
    )