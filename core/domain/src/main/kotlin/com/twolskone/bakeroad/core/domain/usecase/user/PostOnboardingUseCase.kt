package com.twolskone.bakeroad.core.domain.usecase.user

import com.twolskone.bakeroad.core.domain.repository.UserRepository
import com.twolskone.bakeroad.core.model.PreferenceOptionIds
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * 온보딩 설정
 */
class PostOnboardingUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(nickname: String, selectedPreferenceOptions: PreferenceOptionIds): Boolean =
        userRepository.postOnboarding(
            nickname = nickname,
            selectedPreferenceOptions = selectedPreferenceOptions
        ).map { true }.first()
}