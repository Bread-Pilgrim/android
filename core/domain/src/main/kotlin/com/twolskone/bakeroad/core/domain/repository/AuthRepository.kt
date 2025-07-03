package com.twolskone.bakeroad.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(accessToken: String): Flow<Boolean>
    fun verify(): Flow<Unit>
}