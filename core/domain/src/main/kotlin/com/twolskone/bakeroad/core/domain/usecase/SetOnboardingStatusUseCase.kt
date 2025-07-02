package com.twolskone.bakeroad.core.domain.usecase

import com.twolskone.bakeroad.core.domain.repository.CacheRepository
import javax.inject.Inject

/**
 * 온보딩 완료 여부 저장
 */
class SetOnboardingStatusUseCase @Inject constructor(private val cacheRepository: CacheRepository) {

    suspend operator fun invoke(isOnboardingCompleted: Boolean) {
        cacheRepository.setOnboardingCompleted(value = isOnboardingCompleted)
    }
}