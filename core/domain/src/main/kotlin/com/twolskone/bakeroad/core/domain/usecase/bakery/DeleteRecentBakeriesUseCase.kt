package com.twolskone.bakeroad.core.domain.usecase.bakery

import com.twolskone.bakeroad.core.common.kotlin.extension.orFalse
import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

/**
 * 최근 조회환 빵집 삭제
 */
class DeleteRecentBakeriesUseCase @Inject constructor(private val bakeryRepository: BakeryRepository) {

    suspend operator fun invoke(): Boolean =
        bakeryRepository.deleteRecentBakeries()
            .map { true }
            .firstOrNull()
            .orFalse()
}