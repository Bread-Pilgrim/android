package com.twolskone.bakeroad.core.remote.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AuthLoginRequest(
    @SerialName("login_type")
    val loginType: String
)