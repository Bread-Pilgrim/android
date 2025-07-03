package com.twolskone.bakeroad.core.remote.model.prefer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreferOptionsResponse(
    @SerialName("bread_type")
    val breadTypes: List<Option>,
    @SerialName("flavor")
    val flavors: List<Option>,
    @SerialName("atmosphere")
    val atmospheres: List<Option>,
    @SerialName("c_area")
    val commercialAreas: List<Option>
) {

    @Serializable
    data class Option(
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String
    )
}