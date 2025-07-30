package com.twolskone.bakeroad.core.remote.datasource.impl

import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.remote.api.ReviewApi
import com.twolskone.bakeroad.core.remote.datasource.ReviewDataSource
import com.twolskone.bakeroad.core.remote.model.emitData
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class ReviewDataSourceImpl @Inject constructor(
    private val api: ReviewApi,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : ReviewDataSource {

    override fun postLike(reviewId: Int) = flow {
        emitData(api.postLike(reviewId = reviewId))
    }.flowOn(networkDispatcher)

    override fun deleteLike(reviewId: Int) = flow {
        emitData(api.deleteLike(reviewId = reviewId))
    }.flowOn(networkDispatcher)
}