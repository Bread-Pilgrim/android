package com.twolskone.bakeroad.core.exception.extension

import com.twolskone.bakeroad.core.exception.ClientException
import java.io.InterruptedIOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException
import retrofit2.HttpException

/**
 * 서버 응답 이전에 발생한 에러 처리
 * @see ClientException
 */
fun Exception.handleNetworkException(): Nothing =
    throw when (this) {
        // HTTP error
        is HttpException -> when (val code = code()) {
            // Client
            in 400..499 -> {
                ClientException(
                    code = code,
                    message = "$[$code] 클라이언트 오류가 발생했습니다."
                )
            }

            // Temporary sever error
            500, 501, 502, 503, 504 -> {
                ClientException(
                    code = code,
                    message = "$[$code] 이용에 불편을 드려 죄송합니다.\n잠시 후 다시 이용해주세요."
                )
            }

            // Unknown
            else -> {
                ClientException(
                    code = code,
                    message = "$[$code] 알 수 없는 오류가 발생했습니다."
                )
            }
        }

        // Server SSL certificate error
        is SSLHandshakeException,
        is SSLPeerUnverifiedException -> {
            ClientException(
                code = 500,
                message = "이용에 불편을 드려 죄송합니다.\n잠시 후 다시 이용해주세요."
            )
        }

        // Network disconnect
        is UnknownHostException,
        is SocketException,
        is SocketTimeoutException,
        is SSLException,
        is InterruptedIOException -> {
            ClientException(
                code = ClientException.ERROR_CODE_NETWORK,
                message = "네트워크 연결이 불안해요.\n잠시 후 다시 이용해주세요."
            )
        }

        // Unknown
        else -> {
            ClientException(
                code = ClientException.ERROR_CODE_UNKNOWN,
                message = "알 수 없는 오류가 발생했습니다."
            )
        }
    }