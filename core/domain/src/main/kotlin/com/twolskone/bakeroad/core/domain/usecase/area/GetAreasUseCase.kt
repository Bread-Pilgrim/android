package com.twolskone.bakeroad.core.domain.usecase.area

import com.twolskone.bakeroad.core.domain.repository.HomeRepository
import com.twolskone.bakeroad.core.model.Area
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class GetAreasUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend operator fun invoke(): List<Area> =
        homeRepository.getAreas().first()
}