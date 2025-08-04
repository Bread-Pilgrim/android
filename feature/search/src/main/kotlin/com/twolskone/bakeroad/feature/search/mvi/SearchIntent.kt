package com.twolskone.bakeroad.feature.search.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent

internal sealed interface SearchIntent : BaseUiIntent {
    data class ChangeSection(val section: SearchSection) : SearchIntent
    data class SearchBakery(val query: String) : SearchIntent
    data class DeleteQuery(val query: String) : SearchIntent
    data object DeleteAllQueries : SearchIntent
    data class ClickBakeryLike(val bakeryId: Int, val isLike: Boolean) : SearchIntent
    data class SetLoading(val loading: Boolean) : SearchIntent
}