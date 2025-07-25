package com.twolskone.bakeroad.feature.bakery.detail.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.feature.bakery.detail.R

@Immutable
internal enum class ReviewTab(@StringRes val labelId: Int) {
    MY_REVIEW(labelId = R.string.feature_bakery_detail_title_my_review),
    ALL_REVIEW(labelId = R.string.feature_bakery_detail_title_review)
}