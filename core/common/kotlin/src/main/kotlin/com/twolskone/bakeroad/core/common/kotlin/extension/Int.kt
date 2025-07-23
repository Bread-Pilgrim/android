package com.twolskone.bakeroad.core.common.kotlin.extension

import java.text.DecimalFormat

fun Int.toCommaString(): String = DecimalFormat("#,###").format(this)

fun Int?.orZero() = this ?: 0