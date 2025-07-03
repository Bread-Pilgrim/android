package com.twolskone.bakeroad.core.domain.repository

import com.twolskone.bakeroad.core.model.PreferenceOptions
import kotlinx.coroutines.flow.Flow

interface PreferRepository {
    fun getOptions(): Flow<PreferenceOptions>
}