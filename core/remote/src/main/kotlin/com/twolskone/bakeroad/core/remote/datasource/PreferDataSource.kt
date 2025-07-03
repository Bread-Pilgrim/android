package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.prefer.PreferOptionsResponse
import kotlinx.coroutines.flow.Flow

interface PreferDataSource {
    fun getOptions(): Flow<PreferOptionsResponse>
}