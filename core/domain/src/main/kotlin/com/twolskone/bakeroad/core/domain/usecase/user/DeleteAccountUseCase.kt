package com.twolskone.bakeroad.core.domain.usecase.user

import com.twolskone.bakeroad.core.common.kotlin.extension.orFalse
import com.twolskone.bakeroad.core.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

/**
 * 계정 탈퇴
 */
class DeleteAccountUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Boolean =
        userRepository.deleteAccount()
            .map { true }
            .firstOrNull()
            .orFalse()
}