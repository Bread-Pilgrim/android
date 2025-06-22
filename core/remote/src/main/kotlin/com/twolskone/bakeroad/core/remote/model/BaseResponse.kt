package com.twolskone.bakeroad.core.remote.model

import com.twolskone.bakeroad.core.common.kotlin.network.exception.BakeRoadException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BaseResponse<T>(
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
    internal data class Token(
        @SerialName("access_token")
        val accessToken: String,
        @SerialName("refresh_token")
        val refreshToken: String
    )
}

internal fun <T> BaseResponse<T>.toData(): T =
    data ?: throw BakeRoadException(code = code, message = message)