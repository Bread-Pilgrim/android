package com.twolskone.bakeroad.core.remote.model

import com.twolskone.bakeroad.core.exception.BakeRoadError
import com.twolskone.bakeroad.core.exception.BakeRoadException
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("status_code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: T? = null,
    @SerialName("token")
    val token: Token? = null
) {

    @Serializable
    data class Token(
        @SerialName("access_token")
        val accessToken: String,
        @SerialName("refresh_token")
        val refreshToken: String
    )
}

// When this function is used with T as Unit type.
internal fun <T> BaseResponse<T>.toDataOrNull(): T? =
    if (code == 200) {
        data
    } else {
        throw BakeRoadException(
            statusCode = code,
            error = runCatching {
                (data as? String)?.let { BakeRoadError.valueOf(it) }
            }.getOrDefault(null),
            message = message
        )
    }

internal fun <T> BaseResponse<T>.toData(): T =
    if (code == 200) {
        data ?: throw BakeRoadException(
            statusCode = BakeRoadException.STATUS_CODE_UNKNOWN,
            message = message
        )
    } else {
        throw BakeRoadException(
            statusCode = code,
            error = runCatching {
                (data as? String)?.let { BakeRoadError.valueOf(it) }
            }.getOrDefault(null),
            message = message
        )
    }

internal suspend fun <T : BaseResponse<Unit>> FlowCollector<Unit>.emitUnit(response: T) {
    emit(response.toDataOrNull() ?: Unit)
}

internal suspend fun <T : BaseResponse<R>, R : Any> FlowCollector<R>.emitData(response: T) {
    emit(response.toData())
}