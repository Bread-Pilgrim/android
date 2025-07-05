package com.twolskone.bakeroad.core.common.kotlin.extension

fun Boolean?.orFalse() = this ?: false

fun Boolean?.orTrue() = this ?: true