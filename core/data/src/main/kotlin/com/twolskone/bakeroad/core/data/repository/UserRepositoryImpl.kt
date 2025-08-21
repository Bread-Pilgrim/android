package com.twolskone.bakeroad.core.data.repository

import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.data.paging.DefaultPageSize
import com.twolskone.bakeroad.core.datastore.CacheDataSource
import com.twolskone.bakeroad.core.domain.repository.UserRepository
import com.twolskone.bakeroad.core.model.MyBakeryReview
import com.twolskone.bakeroad.core.model.PreferenceOptionIds
import com.twolskone.bakeroad.core.model.Profile
import com.twolskone.bakeroad.core.model.paging.Paging
import com.twolskone.bakeroad.core.remote.datasource.UserDataSource
import com.twolskone.bakeroad.core.remote.model.user.OnboardingRequest
import com.twolskone.bakeroad.core.remote.model.user.PreferencesPatchRequest
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class UserRepositoryImpl @Inject constructor(
    private val cacheDataSource: CacheDataSource,
    private val userDataSource: UserDataSource,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : UserRepository {

    override suspend fun getOnboardingCompleted(): Boolean {
        return cacheDataSource.getOnboardingCompleted()
    }

    override suspend fun setOnboardingCompleted(value: Boolean) {
        cacheDataSource.setOnboardingCompleted(value)
    }

    override fun postOnboarding(nickname: String, selectedPreferenceOptions: PreferenceOptionIds): Flow<Unit> {
        val request = OnboardingRequest(
            nickname = nickname,
            breadTypes = selectedPreferenceOptions.breadTypes,
            flavors = selectedPreferenceOptions.flavors,
            atmospheres = selectedPreferenceOptions.atmospheres
        )
        return userDataSource.postOnboarding(request = request)
    }

    override fun patchPreferences(addPreferences: List<Int>, deletePreferences: List<Int>): Flow<Unit> {
        val request = PreferencesPatchRequest(addPreferences = addPreferences, deletePreferences = deletePreferences)
        return userDataSource.patchPreferences(request)
    }

    override fun getMyReviews(page: Int): Flow<Paging<MyBakeryReview>> = flow {
        val response = userDataSource.getMyReviews(pageNo = page, pageSize = DefaultPageSize)
        val paging = Paging(
            list = response.items.map { it.toExternalModel() },
            page = page,
            hasNext = response.hasNext
        )
        emit(paging)
    }.flowOn(networkDispatcher)

    override fun getPreferences(): Flow<PreferenceOptionIds> {
        return userDataSource.getPreferences()
            .map { response ->
                PreferenceOptionIds(
                    flavors = response.flavors.map { it.preferenceId },
                    breadTypes = response.breadTypes.map { it.preferenceId },
                    atmospheres = response.atmospheres.map { it.preferenceId }
                )
            }
    }

    override fun getProfile(): Flow<Profile> {
        return userDataSource.getProfile()
            .map { profile -> profile.toExternalModel() }
    }
}