package com.twolskone.bakeroad.core.model

import kotlinx.serialization.Serializable

/**
 * 빵말정산 날짜
 * @property year   년
 * @property month  월
 */
@Serializable
data class ReportDate(
    val year: Int,
    val month: Int
)