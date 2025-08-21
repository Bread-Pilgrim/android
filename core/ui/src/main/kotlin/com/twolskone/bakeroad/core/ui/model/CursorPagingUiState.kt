package com.twolskone.bakeroad.core.ui.model

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.model.paging.CursorPaging
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

/**
 * 커서 페이징
 * @property list           전체 데이터 목록
 * @property isLoading      페이징 진행 여부
 * @property currentCursor  현재 커서
 * @property nextCursor     다음 커서
 */
@Immutable
data class CursorPagingUiState<T>(
    val list: PersistentList<T> = persistentListOf(),
    val isLoading: Boolean = false,
    val currentCursor: String = "0",
    val nextCursor: String = "0"
) {

    val canRequest: Boolean
        get() = !isLoading && nextCursor.isNotEmpty()

    fun merge(next: CursorPaging<T>) = copy(
        list = list.addAll(next.list),
        isLoading = false,
        currentCursor = next.currentCursor,
        nextCursor = next.nextCursor
    )

    fun refresh(next: CursorPaging<T>) = copy(
        list = next.list.toPersistentList(),
        isLoading = false,
        currentCursor = next.currentCursor,
        nextCursor = next.nextCursor
    )
}