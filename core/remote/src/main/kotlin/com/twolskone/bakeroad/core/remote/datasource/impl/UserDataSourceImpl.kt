package com.twolskone.bakeroad.core.remote.datasource.impl

import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.remote.api.UserApi
import com.twolskone.bakeroad.core.remote.datasource.UserDataSource
import com.twolskone.bakeroad.core.remote.model.emitData
import com.twolskone.bakeroad.core.remote.model.emitUnit
import com.twolskone.bakeroad.core.remote.model.toData
import com.twolskone.bakeroad.core.remote.model.user.MyBakeryReviewsResponse
import com.twolskone.bakeroad.core.remote.model.user.OnboardingRequest
import com.twolskone.bakeroad.core.remote.model.user.PreferencesGetResponse
import com.twolskone.bakeroad.core.remote.model.user.PreferencesPatchRequest
import com.twolskone.bakeroad.core.remote.model.user.ProfileResponse
import com.twolskone.bakeroad.core.remote.model.user.ReportMonthlyListResponse
import com.twolskone.bakeroad.core.remote.model.user.ReportResponse
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class UserDataSourceImpl @Inject constructor(
    private val api: UserApi,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : UserDataSource {

    override fun postOnboarding(request: OnboardingRequest): Flow<Unit> = flow {
        emitUnit(api.postOnboarding(request = request))
    }.flowOn(networkDispatcher)

    override fun patchPreferences(request: PreferencesPatchRequest): Flow<Unit> = flow {
        emitUnit(api.patchPreferences(request))
    }.flowOn(networkDispatcher)

    override suspend fun getMyReviews(cursor: String, pageSize: Int): MyBakeryReviewsResponse {
        val response = api.getMyReviews(cursorValue = cursor, pageSize = pageSize)
        return response.toData()
    }

    override fun getPreferences(): Flow<PreferencesGetResponse> = flow {
        emitData(api.getPreferences())
    }.flowOn(networkDispatcher)

    override fun getProfile(): Flow<ProfileResponse> = flow {
        emitData(api.getProfile())
    }.flowOn(networkDispatcher)

    override suspend fun getReportMonthlyList(cursor: String, pageSize: Int): ReportMonthlyListResponse {
        val response = api.getReportMonthlyList(cursorValue = cursor, pageSize = pageSize)
        return response.toData()
    }

    override fun getReport(year: Int, month: Int): Flow<ReportResponse> = flow {
        emitData(api.getReport(year = year, month = month))
    }.flowOn(networkDispatcher)

    override fun enableBadge(badgeId: Int): Flow<Unit> = flow {
        emitUnit(api.enableBadge(badgeId = badgeId))
    }.flowOn(networkDispatcher)

    override fun disableBadge(badgeId: Int): Flow<Unit> = flow {
        emitUnit(api.disableBadge(badgeId = badgeId))
    }.flowOn(networkDispatcher)
}