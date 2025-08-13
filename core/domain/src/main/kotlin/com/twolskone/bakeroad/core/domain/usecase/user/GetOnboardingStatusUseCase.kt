package com.twolskone.bakeroad.core.domain.usecase.user

import com.twolskone.bakeroad.core.domain.repository.UserRepository
import javax.inject.Inject

/**
 * 온보딩 완료 여부 조회
 * - 스플래시
 * - 로그인
 */
class GetOnboardingStatusUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Boolean =
        userRepository.getOnboardingCompleted()
}