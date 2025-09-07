package com.twolskone.bakeroad.core.domain.usecase.auth

import com.twolskone.bakeroad.core.common.kotlin.extension.orFalse
import com.twolskone.bakeroad.core.domain.repository.AuthRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

/**
 * 로그아웃
 */
class LogoutUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(): Boolean =
        authRepository.logout()
            .map { true }
            .firstOrNull()
            .orFalse()
}