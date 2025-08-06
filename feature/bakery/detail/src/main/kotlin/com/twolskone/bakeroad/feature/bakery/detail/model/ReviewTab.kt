package com.twolskone.bakeroad.feature.bakery.detail.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.feature.bakery.detail.R

@Immutable
internal enum class ReviewTab(@StringRes val labelRes: Int) {
    ALL_REVIEW(labelRes = R.string.feature_bakery_detail_title_review),
    MY_REVIEW(labelRes = R.string.feature_bakery_detail_title_my_review)
}