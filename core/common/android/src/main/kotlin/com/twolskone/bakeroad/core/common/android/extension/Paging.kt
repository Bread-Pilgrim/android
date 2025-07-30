package com.twolskone.bakeroad.core.common.android.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent
import com.twolskone.bakeroad.core.common.android.base.BaseUiSideEffect
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import kotlinx.coroutines.flow.filterIsInstance
import timber.log.Timber

val <T : Any> LazyPagingItems<T>.isLoading
    get() = loadState.refresh is LoadState.Loading

val <T : Any> LazyPagingItems<T>.isError
    get() = loadState.refresh is LoadState.Error

val <T : Any> LazyPagingItems<T>.isEmpty
    get() = loadState.append.endOfPaginationReached && itemCount == 0

val <T : Any> LazyPagingItems<T>.emptyState
    get() = isEmpty || (isLoading && itemCount == 0)

@Composable
fun <US : BaseUiState, I : BaseUiIntent, SE : BaseUiSideEffect> LazyPagingItems<*>.ObserveError(viewModel: BaseViewModel<US, I, SE>) {
    LaunchedEffect(Unit) {
        snapshotFlow { loadState.refresh }
            .filterIsInstance<LoadState.Error>()
            .collect {
                Timber.e("!!!!!!!")
                viewModel.handleException(it.error)
            }
    }
}