package com.twolskone.bakeroad.core.domain.usecase.user

import com.twolskone.bakeroad.core.domain.repository.UserRepository
import com.twolskone.bakeroad.core.model.Profile
import javax.inject.Inject
import kotlinx.coroutines.flow.first

/**
 * 내 프로필 조회
 */
class GetMyProfileUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Profile =
        userRepository.getProfile().first()
}