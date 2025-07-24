package com.twolskone.bakeroad.core.common.android.base.extension

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

val <T : Any> LazyPagingItems<T>.isLoading
    get() = loadState.refresh is LoadState.Loading

val <T : Any> LazyPagingItems<T>.isError
    get() = loadState.refresh is LoadState.Error

val <T : Any> LazyPagingItems<T>.isEmpty
    get() = loadState.append.endOfPaginationReached && itemCount == 0

val <T : Any> LazyPagingItems<T>.emptyState
    get() = isEmpty || (isLoading && itemCount == 0)
