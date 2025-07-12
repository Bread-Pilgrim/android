package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.area.AreaResponse
import kotlinx.coroutines.flow.Flow

interface AreaDataSource {
    fun getAreas(): Flow<List<AreaResponse>>
}