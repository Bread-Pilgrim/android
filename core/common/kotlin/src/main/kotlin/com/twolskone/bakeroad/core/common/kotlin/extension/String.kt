package com.twolskone.bakeroad.core.common.kotlin.extension

fun String?.toIntOrZero(): Int = this?.toIntOrNull() ?: 0