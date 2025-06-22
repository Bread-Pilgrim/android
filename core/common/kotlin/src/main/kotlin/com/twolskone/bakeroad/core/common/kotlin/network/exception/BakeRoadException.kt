package com.twolskone.bakeroad.core.common.kotlin.network.exception

/**
 * 서버에서 정의한 에러
 */
class BakeRoadException(
    val code: Int,
    override val message: String,
    override val cause: Throwable? = null
) : BaseException(message, cause)