package com.twolskone.bakeroad.core.common.kotlin.network.exception

/**
 * 서버에서 정의할 수 없는 에러
 */
class NetworkException(
    val code: Int,
    override val message: String,
    override val cause: Throwable? = null
) : BaseException(message, cause)