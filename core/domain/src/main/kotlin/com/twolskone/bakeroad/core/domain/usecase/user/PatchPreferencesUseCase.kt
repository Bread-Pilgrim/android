package com.twolskone.bakeroad.core.domain.usecase.user

import com.twolskone.bakeroad.core.common.kotlin.extension.orFalse
import com.twolskone.bakeroad.core.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

/**
 * 취향 변경
 */
class PatchPreferencesUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(addPreferences: List<Int>, deletePreferences: List<Int>): Boolean =
        userRepository.patchPreferences(addPreferences = addPreferences, deletePreferences = deletePreferences)
            .map { true }
            .firstOrNull()
            .orFalse()
}