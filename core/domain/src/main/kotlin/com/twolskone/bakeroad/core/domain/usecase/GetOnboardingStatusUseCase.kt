package com.twolskone.bakeroad.core.domain.usecase

import com.twolskone.bakeroad.core.domain.repository.OnboardingRepository
import javax.inject.Inject

/**
 * 온보딩 완료 여부 조회
 * - 인트로 화면 (스플래쉬, 로그인)
 */
class GetOnboardingStatusUseCase @Inject constructor(private val onboardingRepository: OnboardingRepository) {

    suspend operator fun invoke(): Boolean =
        onboardingRepository.isOnboardingCompleted()
}