package com.twolskone.bakeroad.feature.review.myreviews

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.domain.usecase.review.DeleteReviewLikeUseCase
import com.twolskone.bakeroad.core.domain.usecase.review.PostReviewLikeUseCase
import com.twolskone.bakeroad.core.domain.usecase.user.GetMyBakeryReviewsUseCase
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.core.model.paging.startPage
import com.twolskone.bakeroad.feature.review.myreviews.mvi.MyReviewsIntent
import com.twolskone.bakeroad.feature.review.myreviews.mvi.MyReviewsSideEffect
import com.twolskone.bakeroad.feature.review.myreviews.mvi.MyReviewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
internal class MyReviewsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMyBakeryReviewsUseCase: GetMyBakeryReviewsUseCase,
    private val postReviewLikeUseCase: PostReviewLikeUseCase,
    private val deleteReviewLikeUseCase: DeleteReviewLikeUseCase
) : BaseViewModel<MyReviewsState, MyReviewsIntent, MyReviewsSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): MyReviewsState {
        return MyReviewsState()
    }

    init {
        getMyReviews(refresh = true)
    }

    override fun handleException(cause: Throwable) {
        Timber.e(cause)
        when (cause) {
            is ClientException -> {
                showSnackbar(
                    type = SnackbarType.ERROR,
                    message = cause.message,
                    messageRes = cause.error?.messageRes
                )
            }

            is BakeRoadException -> {
                showSnackbar(type = SnackbarType.ERROR, message = cause.message)
            }
        }
    }

    override suspend fun handleIntent(intent: MyReviewsIntent) {
        when (intent) {
            is MyReviewsIntent.GetMyReviews -> getMyReviews(refresh = intent.refresh)

            is MyReviewsIntent.ClickLike -> {
                val index = state.value.paging.list
                    .indexOfFirst { it.id == intent.id }
                    .takeIf { it >= 0 } ?: return
                val review = state.value.paging.list[index]
                val likeCount = review.likeCount.run { if (intent.isLike) plus(1) else minus(1) }
                reduce {
                    copy(
                        paging = paging.copy(
                            list = paging.list.set(
                                index = index,
                                element = review.copy(
                                    isLike = intent.isLike,
                                    likeCount = likeCount
                                )
                            )
                        )
                    )
                }
                if (intent.isLike) {
                    postReviewLikeUseCase(reviewId = intent.id)
                } else {
                    deleteReviewLikeUseCase(reviewId = intent.id)
                }
            }
        }
    }

    private fun getMyReviews(refresh: Boolean) {
        launch {
            if (!refresh && !state.value.paging.canRequest) {
                Timber.i("xxx MyReviewsPaging is loading or last page.")
                return@launch
            }
            reduce { copy(paging = paging.copy(isLoading = refresh)) }
            val nextPaging = getMyBakeryReviewsUseCase(page = if (refresh) startPage else state.value.paging.nextPage)
            reduce {
                copy(
                    paging = if (refresh) {
                        paging.refresh(nextPaging)
                    } else {
                        paging.merge(nextPaging)
                    }
                )
            }
        }
    }
}