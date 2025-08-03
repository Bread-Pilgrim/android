package com.twolskone.bakeroad.feature.review.write

import android.app.Activity
import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.common.kotlin.extension.orZero
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.domain.usecase.bakery.GetBakeryReviewMenusUseCase
import com.twolskone.bakeroad.core.domain.usecase.bakery.PostBakeryReviewUseCase
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.core.model.WriteBakeryReview
import com.twolskone.bakeroad.feature.review.write.mvi.WriteBakeryReviewIntent
import com.twolskone.bakeroad.feature.review.write.mvi.WriteBakeryReviewSideEffect
import com.twolskone.bakeroad.feature.review.write.mvi.WriteBakeryReviewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import timber.log.Timber

private const val BAKERY_ID = "bakeryId"
internal const val MaxPickImages = 5

@HiltViewModel
internal class WriteReviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBakeryReviewMenusUseCase: GetBakeryReviewMenusUseCase,
    private val postBakeryReviewUseCase: PostBakeryReviewUseCase
) : BaseViewModel<WriteBakeryReviewState, WriteBakeryReviewIntent, WriteBakeryReviewSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): WriteBakeryReviewState {
        return WriteBakeryReviewState()
    }

    private val bakeryId: Int = savedStateHandle.get<Int>(BAKERY_ID).orZero()

    init {
        launch {
            val menus = getBakeryReviewMenusUseCase(bakeryId = bakeryId)
            reduce { copy(menuList = menus.toImmutableList()) }
        }
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

    override suspend fun handleIntent(intent: WriteBakeryReviewIntent) {
        when (intent) {
            is WriteBakeryReviewIntent.SelectMenu -> reduce {
                copy(
                    menuList = menuList.map { menu ->
                        if (menu.id == intent.menuId) {
                            menu.copy(count = if (intent.selected) 1 else 0)
                        } else {
                            menu
                        }
                    }.toImmutableList()
                )
            }

            is WriteBakeryReviewIntent.AddMenuCount -> reduce {
                copy(
                    menuList = menuList.map { menu ->
                        if (menu.id == intent.menuId) {
                            menu.copy(count = menu.count + 1)
                        } else {
                            menu
                        }
                    }.toImmutableList()
                )
            }

            is WriteBakeryReviewIntent.RemoveMenuCount -> reduce {
                copy(
                    menuList = menuList.map { menu ->
                        if (menu.id == intent.menuId) {
                            menu.copy(count = menu.count - 1)
                        } else {
                            menu
                        }
                    }.toImmutableList()
                )
            }

            is WriteBakeryReviewIntent.ChangeRating -> reduce { copy(rating = intent.rating) }

            is WriteBakeryReviewIntent.AddPhotos -> reduce { copy(photoList = photoList.addAll(intent.photos)) }

            is WriteBakeryReviewIntent.DeletePhoto -> reduce { copy(photoList = photoList.removeAt(intent.index)) }

            is WriteBakeryReviewIntent.CheckPrivate -> reduce { copy(isPrivate = intent.checked) }

            is WriteBakeryReviewIntent.UpdateContent -> reduce { copy(content = intent.content) }

            WriteBakeryReviewIntent.CompleteWrite -> writeReview()
        }
    }

    private fun writeReview() = launch {
        val review = with(state.value) {
            WriteBakeryReview(
                rating = rating,
                content = content,
                isPrivate = isPrivate,
                menus = menuList
                    .filter { menu -> menu.count > 0 }
                    .map { WriteBakeryReview.Menu(id = it.id, quantity = it.count) },
                photos = photoList
            )
        }
        postBakeryReviewUseCase(bakeryId = bakeryId, review = review)
        postSideEffect(WriteBakeryReviewSideEffect.SetResult(code = Activity.RESULT_OK, withFinish = true))
    }
}