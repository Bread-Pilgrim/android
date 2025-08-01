package com.twolskone.bakeroad.core.remote.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LoginRequest(
    @SerialName("login_type")
    val loginType: String
)