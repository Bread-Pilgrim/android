package com.twolskone.bakeroad.core.remote.datasource

import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    fun login(accessToken: String): Flow<Boolean>
    fun verify(): Flow<Unit>
}