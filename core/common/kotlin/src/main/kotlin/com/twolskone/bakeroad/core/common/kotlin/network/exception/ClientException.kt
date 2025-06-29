package com.twolskone.bakeroad.core.common.kotlin.network.exception

/**
 * 서버에서 정의할 수 없는 에러
 */
class ClientException(
    val code: Int,
    override val message: String,
    override val cause: Throwable? = null
) : BaseException(message, cause) {

    companion object {
        const val ERROR_CODE_EMPTY_TOKEN = 1000 // 저장된 토큰이 없음. (신규 및 재설치 사용자)
    }
}