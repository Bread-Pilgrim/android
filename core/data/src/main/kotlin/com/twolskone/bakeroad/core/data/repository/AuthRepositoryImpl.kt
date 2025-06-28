package com.twolskone.bakeroad.core.data.repository

import com.twolskone.bakeroad.core.domain.repository.AuthRepository
import com.twolskone.bakeroad.core.remote.datasource.AuthDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

internal class AuthRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource) : AuthRepository {

    override fun login(accessToken: String): Flow<Unit> {
        return authDataSource.login(accessToken)
    }

    override fun verify(): Flow<Unit> {
        return authDataSource.verify()
    }
}