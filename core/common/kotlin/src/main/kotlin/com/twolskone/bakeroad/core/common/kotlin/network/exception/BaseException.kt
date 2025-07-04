package com.twolskone.bakeroad.core.common.kotlin.network.exception

import java.io.IOException

open class BaseException(
    override val message: String,
    override val cause: Throwable? = null
) : IOException(message, cause)