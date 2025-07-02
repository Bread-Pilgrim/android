package com.twolskone.bakeroad.core.domain.usecase

import com.twolskone.bakeroad.core.domain.repository.CacheRepository
import javax.inject.Inject

/**
 * 온보딩 완료 여부 조회
 */
class GetOnboardingStatusUseCase @Inject constructor(private val cacheRepository: CacheRepository) {

    suspend operator fun invoke(): Boolean = cacheRepository.isOnboardingCompleted()
}