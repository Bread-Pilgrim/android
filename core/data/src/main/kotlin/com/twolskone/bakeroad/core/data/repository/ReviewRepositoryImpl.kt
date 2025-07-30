package com.twolskone.bakeroad.core.data.repository

import com.twolskone.bakeroad.core.domain.repository.ReviewRepository
import com.twolskone.bakeroad.core.remote.datasource.ReviewDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ReviewRepositoryImpl @Inject constructor(
    private val reviewDataSource: ReviewDataSource
) : ReviewRepository {

    override fun postLike(reviewId: Int): Flow<Pair<Int, Boolean>> {
        return reviewDataSource.postLike(reviewId = reviewId)
            .map { it.reviewId to it.isLike }
    }

    override fun deleteLike(reviewId: Int): Flow<Pair<Int, Boolean>> {
        return reviewDataSource.deleteLike(reviewId = reviewId)
            .map { it.reviewId to it.isLike }
    }
}