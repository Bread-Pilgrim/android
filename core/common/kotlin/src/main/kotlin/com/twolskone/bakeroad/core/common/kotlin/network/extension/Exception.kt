package com.twolskone.bakeroad.core.common.kotlin.network.extension

import com.twolskone.bakeroad.core.common.kotlin.network.exception.NetworkException
import java.io.InterruptedIOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException
import retrofit2.HttpException

const val ERROR_CODE_TEMPORARY_SERVER = 500
const val ERROR_CODE_NETWORK_CONNECTION = 0
const val ERROR_CODE_UNKNOWN = -1

/**
 * 서버 응답 이전에 발생한 에러 핸들
 */
fun Exception.handleNetworkException(): Nothing =
    throw when (this) {
        // HTTP error
        is HttpException -> when (val code = code()) {
            // Client
            in 400..499 -> {
                NetworkException(
                    code = code,
                    message = "$[$code] 클라이언트 오류가 발생했습니다."
                )
            }

            // Temporary sever error
            500, 501, 502, 503, 504 -> {
                NetworkException(
                    code = ERROR_CODE_TEMPORARY_SERVER,
                    message = "$[$code] 이용에 불편을 드려 죄송합니다.\n잠시 후 다시 이용해주세요."
                )
            }

            // Unknown
            else -> {
                NetworkException(
                    code = code,
                    message = "$[$code] 알 수 없는 오류가 발생했습니다."
                )
            }
        }

        // Server SSL certificate error
        is SSLHandshakeException,
        is SSLPeerUnverifiedException -> {
            NetworkException(
                code = ERROR_CODE_TEMPORARY_SERVER,
                message = "이용에 불편을 드려 죄송합니다.\n잠시 후 다시 이용해주세요."
            )
        }

        // Network disconnect
        is UnknownHostException,
        is SocketException,
        is SocketTimeoutException,
        is SSLException,
        is InterruptedIOException -> {
            NetworkException(
                code = ERROR_CODE_NETWORK_CONNECTION,
                message = "네트워크 연결이 불안해요.\n잠시 후 다시 이용해주세요."
            )
        }

        // Unknown
        else -> {
            NetworkException(
                code = ERROR_CODE_UNKNOWN,
                message = "알 수 없는 오류가 발생했습니다."
            )
        }
    }