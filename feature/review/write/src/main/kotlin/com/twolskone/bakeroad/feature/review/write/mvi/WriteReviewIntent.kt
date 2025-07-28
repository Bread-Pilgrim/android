package com.twolskone.bakeroad.feature.review.write.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent

internal sealed interface WriteReviewIntent : BaseUiIntent {
    data class SelectMenu(val menuId: Int, val selected: Boolean) : WriteReviewIntent
    data class AddMenuCount(val menuId: Int) : WriteReviewIntent
    data class RemoveMenuCount(val menuId: Int) : WriteReviewIntent
    data class ChangeRating(val rating: Float) : WriteReviewIntent
    data class AddPhotos(val photos: List<String>) : WriteReviewIntent
    data class DeletePhoto(val index: Int) : WriteReviewIntent
}