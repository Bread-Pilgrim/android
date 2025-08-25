package com.twolskone.bakeroad.core.domain.usecase.user

import com.twolskone.bakeroad.core.common.kotlin.extension.orFalse
import com.twolskone.bakeroad.core.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

/**
 * 대표뱃지 해제
 */
class DisableBadgeUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(badgeId: Int): Boolean =
        userRepository.disableBadge(badgeId = badgeId)
            .map { true }
            .firstOrNull()
            .orFalse()
}