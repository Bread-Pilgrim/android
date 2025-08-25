package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.badge.BadgeResponse
import kotlinx.coroutines.flow.Flow

interface BadgeDataSource {
    fun getBadges(): Flow<List<BadgeResponse>>
}