package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.preference.PreferenceOptionsResponse
import kotlinx.coroutines.flow.Flow

interface PreferDataSource {
    fun getOptions(): Flow<PreferenceOptionsResponse>
}