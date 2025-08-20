package com.twolskone.bakeroad.core.remote.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreferencesPatchRequest(
    @SerialName("add_preferences")
    val addPreferences: List<Int>,
    @SerialName("delete_preferences")
    val deletePreferences: List<Int>
)