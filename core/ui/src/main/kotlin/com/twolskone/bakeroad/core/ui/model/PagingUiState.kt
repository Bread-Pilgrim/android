package com.twolskone.bakeroad.core.ui.model

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.model.paging.Paging
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

/**
 * 페이징 상태
 * @property list       전체 데이터 목록
 * @property isLoading  페이징 진행 여부
 * @property page       현재 페이지
 * @property hasNext    다음 페이지 여부
 */
@Immutable
data class PagingUiState<T>(
    val list: PersistentList<T> = persistentListOf(),
    val isLoading: Boolean = false,
    val page: Int = 0,
    val hasNext: Boolean = true
) {

    val nextPage: Int
        get() = page + 1

    val canRequest: Boolean
        get() = !isLoading && hasNext

    fun merge(next: Paging<T>) = copy(
        list = list.addAll(next.list),
        isLoading = false,
        page = next.page,
        hasNext = next.hasNext
    )
}

fun <T> Paging<T>.toUiState() = PagingUiState(
    list = list.toPersistentList(),
    isLoading = false,
    page = page,
    hasNext = hasNext
)