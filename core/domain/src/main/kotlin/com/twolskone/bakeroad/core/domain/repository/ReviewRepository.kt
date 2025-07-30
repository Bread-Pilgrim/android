package com.twolskone.bakeroad.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    fun postLike(reviewId: Int): Flow<Pair<Int, Boolean>>
    fun deleteLike(reviewId: Int): Flow<Pair<Int, Boolean>>
}