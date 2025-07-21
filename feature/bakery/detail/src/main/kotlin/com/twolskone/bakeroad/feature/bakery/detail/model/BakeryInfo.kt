package com.twolskone.bakeroad.feature.bakery.detail.model

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus

@Immutable
internal data class BakeryInfo(
    val name: String,
    val rating: Float,
    val reviewCount: Int,
    val address: String,
    val phone: String,
    val openStatus: BakeryOpenStatus,
    val isLike: Boolean,
)

internal fun BakeryDetail.toBakeryInfo(): BakeryInfo =
    BakeryInfo(
        name = name,
        rating = rating,
        reviewCount = reviewCount,
        address = address,
        phone = phone,
        openStatus = openStatus,
        isLike = isLike
    )