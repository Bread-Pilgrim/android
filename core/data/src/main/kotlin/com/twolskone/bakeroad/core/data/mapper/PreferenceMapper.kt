package com.twolskone.bakeroad.core.data.mapper

import com.twolskone.bakeroad.core.model.PreferenceOption
import com.twolskone.bakeroad.core.model.PreferenceOptionType
import com.twolskone.bakeroad.core.model.PreferenceOptions
import com.twolskone.bakeroad.core.remote.model.preference.PreferenceOptionsResponse

internal fun PreferenceOptionsResponse.toExternalModel(): PreferenceOptions =
    PreferenceOptions(
        flavors = flavors.map { flavor -> flavor.toExternalModel(type = PreferenceOptionType.FLAVOR) },
        breadTypes = breadTypes.map { breadType -> breadType.toExternalModel(type = PreferenceOptionType.BREAD_TYPE) },
        atmospheres = atmospheres.map { atmosphere -> atmosphere.toExternalModel(type = PreferenceOptionType.ATMOSPHERE) }
    )

private fun PreferenceOptionsResponse.Option.toExternalModel(type: PreferenceOptionType): PreferenceOption =
    PreferenceOption(type = type, id = id, name = name)