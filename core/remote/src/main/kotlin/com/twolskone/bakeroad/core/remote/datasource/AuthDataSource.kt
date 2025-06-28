package com.twolskone.bakeroad.core.remote.datasource

import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    fun login(accessToken: String): Flow<Unit>
    fun verify(): Flow<Unit>
}