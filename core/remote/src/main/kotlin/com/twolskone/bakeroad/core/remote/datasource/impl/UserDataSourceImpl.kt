package com.twolskone.bakeroad.core.remote.datasource.impl

import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.remote.api.UserApi
import com.twolskone.bakeroad.core.remote.datasource.UserDataSource
import com.twolskone.bakeroad.core.remote.model.emitData
import com.twolskone.bakeroad.core.remote.model.emitUnit
import com.twolskone.bakeroad.core.remote.model.user.UserOnboardingRequest
import com.twolskone.bakeroad.core.remote.model.user.UserPreferencesRequest
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class UserDataSourceImpl @Inject constructor(
    private val api: UserApi,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : UserDataSource {

    override fun postOnboarding(request: UserOnboardingRequest): Flow<String> = flow {
        emitData(api.postOnboarding(request = request))
    }.flowOn(networkDispatcher)

    override fun patchPreferences(request: UserPreferencesRequest): Flow<Unit> = flow {
        emitUnit(api.patchPreferences(request))
    }.flowOn(networkDispatcher)
}