package com.twolskone.bakeroad.core.common.kotlin.network.exception

open class BaseException(
    override val message: String,
    override val cause: Throwable? = null
) : Exception(message, cause)