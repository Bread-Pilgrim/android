package com.twolskone.bakeroad.core.domain.usecase.user

import com.twolskone.bakeroad.core.domain.repository.UserRepository
import com.twolskone.bakeroad.core.model.PreferenceOptionIds
import javax.inject.Inject
import kotlinx.coroutines.flow.first

/**
 * 취향 조회
 */
class GetPreferencesUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(): PreferenceOptionIds =
        userRepository.getPreferences().first()
}