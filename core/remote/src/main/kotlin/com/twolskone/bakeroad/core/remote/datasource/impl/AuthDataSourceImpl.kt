package com.twolskone.bakeroad.core.remote.datasource.impl

import android.content.Context
import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.exception.ClientException
import com.twolskone.bakeroad.core.datastore.TokenDataSource
import com.twolskone.bakeroad.core.remote.R
import com.twolskone.bakeroad.core.remote.api.AuthApi
import com.twolskone.bakeroad.core.remote.datasource.AuthDataSource
import com.twolskone.bakeroad.core.remote.model.emitRemote
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class AuthDataSourceImpl @Inject constructor(
    private val api: AuthApi,
    private val tokenDataSource: TokenDataSource,
    @Dispatcher(BakeRoadDispatcher.IO) val networkDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context
) : AuthDataSource {

    override fun login(accessToken: String): Flow<Unit> = flow {
        emitRemote(api.login(accessToken))
    }.flowOn(networkDispatcher)

    override fun verify(): Flow<Unit> = flow {
        val (accessToken, refreshToken) = tokenDataSource.getTokens()
        if (accessToken.isEmpty() || refreshToken.isEmpty()) {
            throw ClientException(
                code = ClientException.EMPTY_TOKEN_ERROR_CODE,
                message = context.getString(R.string.core_remote_error_message_empty_token)
            )
        }

        emitRemote(api.verify(accessToken, refreshToken))
    }.flowOn(networkDispatcher)
}