package com.twolskone.bakeroad.core.domain.usecase.user

import com.twolskone.bakeroad.core.domain.repository.UserRepository
import javax.inject.Inject

/**
 * 온보딩 완료 여부 저장
 */
class SetOnboardingStatusUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(isOnboardingCompleted: Boolean) {
        userRepository.setOnboardingCompleted(value = isOnboardingCompleted)
    }
}