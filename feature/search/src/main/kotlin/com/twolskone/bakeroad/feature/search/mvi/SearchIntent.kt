package com.twolskone.bakeroad.feature.search.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent

internal sealed interface SearchIntent : BaseUiIntent {
    data class ChangeSection(val section: SearchSection) : SearchIntent
    data class SearchBakery(val query: String) : SearchIntent
}