package com.twolskone.bakeroad.feature.review.write

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.common.kotlin.extension.orZero
import com.twolskone.bakeroad.core.domain.usecase.GetBakeryReviewMenusUseCase
import com.twolskone.bakeroad.feature.review.write.mvi.WriteReviewIntent
import com.twolskone.bakeroad.feature.review.write.mvi.WriteReviewSideEffect
import com.twolskone.bakeroad.feature.review.write.mvi.WriteReviewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList

private const val BAKERY_ID = "bakeryId"

@HiltViewModel
internal class WriteReviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBakeryReviewMenusUseCase: GetBakeryReviewMenusUseCase
) : BaseViewModel<WriteReviewState, WriteReviewIntent, WriteReviewSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): WriteReviewState {
        return WriteReviewState()
    }

    private val bakeryId: Int = savedStateHandle.get<Int>(BAKERY_ID).orZero()

    init {
        launch {
            val menus = getBakeryReviewMenusUseCase(bakeryId = bakeryId)
            reduce { copy(menus = menus.toImmutableList()) }
        }
    }

    override fun handleException(cause: Throwable) {
        TODO("Not yet implemented")
    }

    override suspend fun handleIntent(intent: WriteReviewIntent) {
        when (intent) {
            is WriteReviewIntent.SelectMenu -> reduce {
                copy(
                    menus = menus.map { menu ->
                        if (menu.id == intent.menuId) {
                            menu.copy(count = if (intent.selected) 1 else 0)
                        } else {
                            menu
                        }
                    }.toImmutableList()
                )
            }

            is WriteReviewIntent.AddMenuCount -> reduce {
                copy(
                    menus = menus.map { menu ->
                        if (menu.id == intent.menuId) {
                            menu.copy(count = menu.count + 1)
                        } else {
                            menu
                        }
                    }.toImmutableList()
                )
            }

            is WriteReviewIntent.RemoveMenuCount -> reduce {
                copy(
                    menus = menus.map { menu ->
                        if (menu.id == intent.menuId) {
                            menu.copy(count = menu.count - 1)
                        } else {
                            menu
                        }
                    }.toImmutableList()
                )
            }
        }
    }
}