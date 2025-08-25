package com.twolskone.bakeroad.core.domain.repository

import com.twolskone.bakeroad.core.model.Badge
import kotlinx.coroutines.flow.Flow

interface BadgeRepository {
    fun getBadges(): Flow<List<Badge>>
}