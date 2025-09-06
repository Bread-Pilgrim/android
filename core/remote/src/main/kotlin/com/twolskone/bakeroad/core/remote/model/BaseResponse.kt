package com.twolskone.bakeroad.core.remote.model

import com.twolskone.bakeroad.core.exception.BakeRoadError
import com.twolskone.bakeroad.core.exception.BakeRoadException
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T, R>(
    @SerialName("status_code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: T? = null,
    @SerialName("extra")
    val extra: R? = null,
    @SerialName("token")
    val token: Token? = null,
    @SerialName("error_usecase")
    val errorUseCase: String? = null
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
internal fun <T, R> BaseResponse<T, R>.toDataOrNull(): T? =
    if (code == 200) {
        data
    } else {
        throw BakeRoadException(
            statusCode = code,
            error = runCatching {
                errorUseCase?.let { BakeRoadError.valueOf(it) }
            }.getOrDefault(null),
            message = message
        )
    }

internal fun <T, R> BaseResponse<T, R>.toData(): T =
    if (code == 200) {
        data ?: throw BakeRoadException(
            statusCode = BakeRoadException.STATUS_CODE_UNKNOWN,
            message = message
        )
    } else {
        throw BakeRoadException(
            statusCode = code,
            error = runCatching {
                errorUseCase?.let { BakeRoadError.valueOf(it) }
            }.getOrDefault(null),
            message = message
        )
    }

internal fun <T, R> BaseResponse<T, R>.toExtraOrNull(): R? =
    if (code == 200) {
        extra
    } else {
        throw BakeRoadException(
            statusCode = code,
            error = runCatching {
                errorUseCase?.let { BakeRoadError.valueOf(it) }
            }.getOrDefault(null),
            message = message
        )
    }

internal suspend fun <R> FlowCollector<Unit>.emitUnit(response: BaseResponse<Unit, R>) {
    emit(response.toDataOrNull() ?: Unit)
}

internal suspend fun <T, R> FlowCollector<T>.emitData(response: BaseResponse<T, R>) {
    emit(response.toData())
}