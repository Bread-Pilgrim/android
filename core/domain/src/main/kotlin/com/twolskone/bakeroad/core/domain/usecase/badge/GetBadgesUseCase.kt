package com.twolskone.bakeroad.core.domain.usecase.badge

import com.twolskone.bakeroad.core.domain.repository.BadgeRepository
import com.twolskone.bakeroad.core.model.Badge
import javax.inject.Inject
import kotlinx.coroutines.flow.first

/**
 * 뱃지 조회
 */
class GetBadgesUseCase @Inject constructor(private val badgeRepository: BadgeRepository) {

    suspend operator fun invoke(): List<Badge> =
        badgeRepository.getBadges().first()
}