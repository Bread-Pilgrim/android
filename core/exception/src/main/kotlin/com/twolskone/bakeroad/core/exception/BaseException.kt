package com.twolskone.bakeroad.core.exception

import java.io.IOException

open class BaseException(
    override val message: String,
    override val cause: Throwable? = null
) : IOException(message, cause)