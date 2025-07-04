package com.twolskone.bakeroad.core.common.kotlin.network.exception

/**
 * 서버에서 정의한 에러
 */
class BakeRoadException(
    val code: Int,
    override val message: String,
    override val cause: Throwable? = null
) : BaseException(message, cause) {

    companion object {
        const val ERROR_CODE_REFRESH_TOKEN_EXPIRED = 1001   // refreshToken 만료 → 로그인 화면
        const val ERROR_CODE_DUPLICATE_ENTRY = 1009         // 중복 데이터
    }
}