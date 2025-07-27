package com.twolskone.bakeroad.feature.review.write.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class WriteReviewState(
    val photoList: ImmutableList<String> = persistentListOf()
) : BaseUiState