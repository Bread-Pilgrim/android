package com.twolskone.bakeroad.feature.search.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SearchState(
    val recentQueryList: ImmutableList<Pair<String, Long>> = persistentListOf(),
    val query: String = ""
) : BaseUiState