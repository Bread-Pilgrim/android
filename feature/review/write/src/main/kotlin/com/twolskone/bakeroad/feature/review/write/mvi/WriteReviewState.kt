package com.twolskone.bakeroad.feature.review.write.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.ReviewMenu
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class WriteReviewState(
    val menus: ImmutableList<ReviewMenu> = persistentListOf(),
    val photoList: ImmutableList<String> = persistentListOf()
) : BaseUiState