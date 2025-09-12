package com.twolskone.bakeroad.core.domain.usecase.user

import com.twolskone.bakeroad.core.domain.repository.UserRepository
import javax.inject.Inject

/**
 * 모든 DataStore 초기화
 */
class DeleteAllDataStoresUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke() = userRepository.clearAllDataStores()
}