package com.twolskone.bakeroad.core.remote.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreferencesGetResponse(
    @SerialName("bread_types")
    val breadTypes: List<Preference> = emptyList(),
    @SerialName("flavors")
    val flavors: List<Preference> = emptyList(),
    @SerialName("atmospheres")
    val atmospheres: List<Preference> = emptyList()
) {

    @Serializable
    data class Preference(
        @SerialName("preference_id")
        val preferenceId: Int = -1,
        @SerialName("preference_name")
        val preferenceName: String = ""
    )
}