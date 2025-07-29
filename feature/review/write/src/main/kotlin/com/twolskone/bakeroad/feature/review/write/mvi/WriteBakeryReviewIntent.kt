package com.twolskone.bakeroad.feature.review.write.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent

internal sealed interface WriteBakeryReviewIntent : BaseUiIntent {
    data class SelectMenu(val menuId: Int, val selected: Boolean) : WriteBakeryReviewIntent
    data class AddMenuCount(val menuId: Int) : WriteBakeryReviewIntent
    data class RemoveMenuCount(val menuId: Int) : WriteBakeryReviewIntent
    data class ChangeRating(val rating: Float) : WriteBakeryReviewIntent
    data class AddPhotos(val photos: List<String>) : WriteBakeryReviewIntent
    data class DeletePhoto(val index: Int) : WriteBakeryReviewIntent
    data class CheckPrivate(val checked: Boolean) : WriteBakeryReviewIntent
    data class UpdateContent(val content: String) : WriteBakeryReviewIntent
    data object CompleteWrite : WriteBakeryReviewIntent
}