package com.twolskone.bakeroad.core.data.repository

import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.domain.repository.BadgeRepository
import com.twolskone.bakeroad.core.model.Badge
import com.twolskone.bakeroad.core.remote.datasource.BadgeDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class BadgeRepositoryImpl @Inject constructor(
    private val badgeDataSource: BadgeDataSource
) : BadgeRepository {

    override fun getBadges(): Flow<List<Badge>> {
        return badgeDataSource.getBadges()
            .map { list ->
                list.map { response ->
                    response.toExternalModel()
                }
            }
    }
}