package com.twolskone.bakeroad.core.exception

/**
 * 서버에서 정의할 수 없는 에러
 * @param code  에러 코드
 * @param error 클라이언트 정의 에러
 */
class ClientException(
    val code: Int,
    val error: ClientError? = null,
    override val message: String
) : BaseException(message) {

    companion object {
        const val ERROR_CODE_NETWORK = 0        // 네트워크 연결 오류
        const val ERROR_CODE_UNKNOWN = -1       // 알 수 없는 오류
        const val ERROR_CODE_EMPTY_TOKEN = 1000 // 저장된 토큰이 없는 오류
    }
}

enum class ClientError(val messageRes: Int) {
    // 클라이언트 오류 (4xx)
    Client(messageRes = R.string.core_exception_client_error_client),

    // 일시적 서버 오류 (5xx)
    TemporaryServer(messageRes = R.string.core_exception_client_error_temporary_server),

    // 네트워크 연결 오류
    Network(messageRes = R.string.core_exception_client_error_network),

    // 알 수 없는 오류
    Unknown(messageRes = R.string.core_exception_client_error_unknown),

    // 저장된 토큰이 없는 오류
    EmptyToken(messageRes = R.string.core_exception_client_error_empty_token)
}