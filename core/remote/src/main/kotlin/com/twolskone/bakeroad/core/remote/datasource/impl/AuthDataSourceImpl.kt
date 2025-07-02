package com.twolskone.bakeroad.core.remote.datasource.impl

import android.content.Context
import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.exception.ClientException
import com.twolskone.bakeroad.core.datastore.CacheDataSource
import com.twolskone.bakeroad.core.datastore.TokenDataSource
import com.twolskone.bakeroad.core.remote.R
import com.twolskone.bakeroad.core.remote.api.AuthApi
import com.twolskone.bakeroad.core.remote.datasource.AuthDataSource
import com.twolskone.bakeroad.core.remote.model.auth.AuthLoginRequest
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

    override fun login(accessToken: String): Flow<Unit> = flow {
        val request = AuthLoginRequest(loginType = "KAKAO") // TODO. 로그인 플랫폼 추가 시, enum 화 및 로직 분기
        emitUnit(api.login(accessToken = accessToken, request = request))
    }.flowOn(networkDispatcher)

    override fun verify(): Flow<Unit> = flow {
        val (accessToken, refreshToken) = tokenDataSource.getTokens()
        // 신규 및 재설치 사용자
        if (accessToken.isEmpty() || refreshToken.isEmpty()) {
            throw ClientException(
                code = ClientException.ERROR_CODE_EMPTY_TOKEN,
                message = context.getString(R.string.core_remote_error_message_empty_token)
            )
        }
        val isOnBoardingCompleted = api.verify(accessToken, refreshToken).toData().isOnboardingCompleted
        cacheDataSource.setOnboardingCompleted(value = isOnBoardingCompleted)
        emit(Unit)
    }.flowOn(networkDispatcher)
}