package com.twolskone.bakeroad.core.remote.datasource.impl

import android.content.Context
import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.datastore.CacheDataSource
import com.twolskone.bakeroad.core.datastore.TokenDataSource
import com.twolskone.bakeroad.core.exception.ClientError
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.core.remote.api.AuthApi
import com.twolskone.bakeroad.core.remote.datasource.AuthDataSource
import com.twolskone.bakeroad.core.remote.model.auth.LoginRequest
import com.twolskone.bakeroad.core.remote.model.emitUnit
import com.twolskone.bakeroad.core.remote.model.toData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class AuthDataSourceImpl @Inject constructor(
    private val api: AuthApi,
    private val tokenDataSource: TokenDataSource,
    private val cacheDataSource: CacheDataSource,
    @Dispatcher(BakeRoadDispatcher.IO) val networkDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context
) : AuthDataSource {

    override fun login(accessToken: String): Flow<Boolean> = flow {
        val request = LoginRequest(loginType = "KAKAO")
        val response = api.login(accessToken = accessToken, request = request)
        val isOnboardingCompleted = response.toData().isOnboardingCompleted

        cacheDataSource.setOnboardingCompleted(value = isOnboardingCompleted)
        emit(isOnboardingCompleted)
    }.flowOn(networkDispatcher)

    override fun verify(): Flow<Unit> = flow {
        val (accessToken, refreshToken) = tokenDataSource.getTokens()
        // 저장된 토큰이 없는 경우 (신규 및 재설치 사용자)
        if (accessToken.isEmpty() || refreshToken.isEmpty()) {
            throw ClientException(
                code = ClientException.ERROR_CODE_EMPTY_TOKEN,
                error = ClientError.EmptyToken,
                message = context.getString(ClientError.EmptyToken.messageId)
            )
        }

        emitUnit(api.verify(accessToken, refreshToken))
    }.flowOn(networkDispatcher)
}