package com.twolskone.bakeroad.core.domain.usecase

import com.twolskone.bakeroad.core.domain.repository.OnboardingRepository
import com.twolskone.bakeroad.core.model.SelectedPreferenceOptions
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class PostOnboardingUseCase @Inject constructor(private val onboardingRepository: OnboardingRepository) {

    suspend operator fun invoke(nickname: String, selectedPreferenceOptions: SelectedPreferenceOptions): String =
        onboardingRepository.postOnboarding(
            nickname = nickname,
            selectedPreferenceOptions = selectedPreferenceOptions
        ).first()
}