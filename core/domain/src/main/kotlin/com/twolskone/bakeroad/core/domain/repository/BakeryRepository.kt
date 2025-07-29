package com.twolskone.bakeroad.core.domain.repository

import androidx.paging.PagingData
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.ReviewMenu
import com.twolskone.bakeroad.core.model.WriteBakeryReview
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.core.model.type.ReviewSortType
import kotlinx.coroutines.flow.Flow

interface BakeryRepository {
    fun getBakeries(areaCodes: String, bakeryType: BakeryType): Flow<PagingData<Bakery>>
    fun getBakeryDetail(bakeryId: Int): Flow<BakeryDetail>
    fun getPreviewReviews(bakeryId: Int): Flow<List<BakeryReview>>
    fun getReviews(myReview: Boolean, bakeryId: Int, sort: ReviewSortType?): Flow<PagingData<BakeryReview>>
    fun getReviewMenus(bakeryId: Int): Flow<List<ReviewMenu>>
    fun writeReview(bakeryId: Int, review: WriteBakeryReview): Flow<Unit>
}