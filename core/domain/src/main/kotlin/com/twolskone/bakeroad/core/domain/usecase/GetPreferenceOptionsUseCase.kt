package com.twolskone.bakeroad.core.domain.usecase

import com.twolskone.bakeroad.core.domain.repository.PreferRepository
import com.twolskone.bakeroad.core.model.PreferenceOptions
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class GetPreferenceOptionsUseCase @Inject constructor(private val preferRepository: PreferRepository) {

    suspend operator fun invoke(): PreferenceOptions =
        preferRepository.getOptions().first()
}