package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.bakery.BakeriesResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryDetailResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryLikeResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryMenuResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryReviewResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryReviewsResponse
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse
import com.twolskone.bakeroad.core.remote.model.bakery.WriteBakeryReviewRequest
import kotlinx.coroutines.flow.Flow

interface BakeryDataSource {
    fun getRecommendPreferenceBakeries(areaCodes: String): Flow<List<RecommendBakeryResponse>>
    fun getRecommendHotBakeries(areaCodes: String): Flow<List<RecommendBakeryResponse>>
    suspend fun getPreferenceBakeries(areaCodes: String, cursorValue: String, pageSize: Int): BakeriesResponse
    suspend fun getHotBakeries(areaCodes: String, cursorValue: String, pageSize: Int): BakeriesResponse
    fun getBakeryDetail(bakeryId: Int): Flow<BakeryDetailResponse>
    fun getPreviewReviews(bakeryId: Int): Flow<List<BakeryReviewResponse>>
    suspend fun getReviews(bakeryId: Int, sort: String, cursorValue: String, pageSize: Int): BakeryReviewsResponse
    suspend fun getMyReviews(bakeryId: Int, cursorValue: String, pageSize: Int): BakeryReviewsResponse
    fun getMenus(bakeryId: Int): Flow<List<BakeryMenuResponse>>
    fun postReview(bakeryId: Int, request: WriteBakeryReviewRequest): Flow<Unit>
    fun postLike(bakeryId: Int): Flow<BakeryLikeResponse>
    fun deleteLike(bakeryId: Int): Flow<BakeryLikeResponse>
}