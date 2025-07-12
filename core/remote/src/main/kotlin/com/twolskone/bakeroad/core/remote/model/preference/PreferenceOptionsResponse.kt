package com.twolskone.bakeroad.core.remote.model.preference

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreferenceOptionsResponse(
    @SerialName("bread_type")
    val breadTypes: List<Option> = emptyList(),
    @SerialName("flavor")
    val flavors: List<Option> = emptyList(),
    @SerialName("atmosphere")
    val atmospheres: List<Option> = emptyList(),
    @SerialName("c_area")
    val commercialAreas: List<Option> = emptyList()
) {

    @Serializable
    data class Option(
        @SerialName("id")
        val id: Int = -1,
        @SerialName("name")
        val name: String = ""
    )
}