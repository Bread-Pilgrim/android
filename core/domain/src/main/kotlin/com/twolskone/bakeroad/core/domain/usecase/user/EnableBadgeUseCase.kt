package com.twolskone.bakeroad.core.domain.usecase.user

import com.twolskone.bakeroad.core.common.kotlin.extension.orFalse
import com.twolskone.bakeroad.core.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

/**
 * 대표뱃지 설정
 */
class EnableBadgeUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(badgeId: Int): Boolean =
        userRepository.enableBadge(badgeId = badgeId)
            .map { true }
            .firstOrNull()
            .orFalse()
}