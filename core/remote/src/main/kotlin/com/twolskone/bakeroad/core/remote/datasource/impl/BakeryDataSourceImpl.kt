package com.twolskone.bakeroad.core.remote.datasource.impl

import android.content.Context
import androidx.core.net.toUri
import com.twolskone.bakeroad.core.common.android.util.FileUtil
import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.remote.api.BakeryApi
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource
import com.twolskone.bakeroad.core.remote.model.bakery.BakeriesResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryDetailResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryLikeResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryMenuResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryReviewResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryReviewsResponse
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse
import com.twolskone.bakeroad.core.remote.model.bakery.WriteBakeryReviewRequest
import com.twolskone.bakeroad.core.remote.model.emitData
import com.twolskone.bakeroad.core.remote.model.emitUnit
import com.twolskone.bakeroad.core.remote.model.toData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

private const val PreviewReviewCount = 3

internal class BakeryDataSourceImpl @Inject constructor(
    private val api: BakeryApi,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context
) : BakeryDataSource {

    override fun getRecommendPreferenceBakeries(areaCodes: String): Flow<List<RecommendBakeryResponse>> = flow {
        emitData(api.getRecommendPreferenceBakeries(areaCode = areaCodes))
    }.flowOn(networkDispatcher)

    override fun getRecommendHotBakeries(areaCodes: String): Flow<List<RecommendBakeryResponse>> = flow {
        emitData(api.getRecommendHotBakeries(areaCode = areaCodes))
    }.flowOn(networkDispatcher)

    override suspend fun getPreferenceBakeries(
        areaCodes: String,
        cursorValue: String,
        pageSize: Int
    ): BakeriesResponse {
        val response = api.getPreferenceBakeries(
            areaCode = areaCodes,
            cursorValue = cursorValue,
            pageSize = pageSize
        )
        return response.toData()
    }

    override suspend fun getHotBakeries(areaCodes: String, cursorValue: String, pageSize: Int): BakeriesResponse {
        val response = api.getHotBakeries(
            areaCode = areaCodes,
            cursorValue = cursorValue,
            pageSize = pageSize
        )
        return response.toData()
    }

    override fun getBakeryDetail(bakeryId: Int): Flow<BakeryDetailResponse> = flow {
        emitData(api.getBakeryDetail(bakeryId = bakeryId))
    }.flowOn(networkDispatcher)

    override fun getPreviewReviews(bakeryId: Int): Flow<List<BakeryReviewResponse>> = flow {
        val items = api.getPreviewReviews(bakeryId = bakeryId)
            .toData()
            .items
            .take(PreviewReviewCount)
        emit(items)
    }.flowOn(networkDispatcher)

    override suspend fun getReviews(
        bakeryId: Int,
        sort: String,
        cursorValue: String,
        pageSize: Int
    ): BakeryReviewsResponse {
        val response = api.getReviews(
            bakeryId = bakeryId,
            sort = sort,
            cursorValue = cursorValue,
            pageSize = pageSize
        )
        return response.toData()
    }

    override suspend fun getMyReviews(
        bakeryId: Int,
        cursorValue: String,
        pageSize: Int
    ): BakeryReviewsResponse {
        val response = api.getMyReviews(
            bakeryId = bakeryId,
            cursorValue = cursorValue,
            pageSize = pageSize
        )
        return response.toData()
    }

    override fun getMenus(bakeryId: Int): Flow<List<BakeryMenuResponse>> = flow {
        emitData(api.getMenus(bakeryId = bakeryId))
    }.flowOn(networkDispatcher)

    override fun postReview(bakeryId: Int, request: WriteBakeryReviewRequest): Flow<Unit> = flow {
        val imageMultipartList = request.reviewImgs.map {
            val uri = it.toUri()
            val imageFile = FileUtil.getImageFileFromUri(context = context, uri = uri)
            val imageRequestBody = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("review_imgs", imageFile.name, imageRequestBody)
        }
        emitUnit(
            api.postReview(
                bakeryId = bakeryId,
                rating = request.rating.toString().toRequestBody(contentType = "text/plain".toMediaType()),
                content = request.content.toRequestBody(contentType = "text/plain".toMediaType()),
                isPrivate = request.isPrivate.toString().toRequestBody(contentType = "text/plain".toMediaType()),
                consumedMenus = Json.encodeToString(request.consumedMenus).toRequestBody(contentType = "application/json".toMediaType()),
                reviewImgs = imageMultipartList
            )
        )
    }.flowOn(networkDispatcher)

    override fun postLike(bakeryId: Int): Flow<BakeryLikeResponse> = flow {
        emitData(api.postLike(bakeryId = bakeryId))
    }.flowOn(networkDispatcher)

    override fun deleteLike(bakeryId: Int): Flow<BakeryLikeResponse> = flow {
        emitData(api.deleteLike(bakeryId = bakeryId))
    }.flowOn(networkDispatcher)
}