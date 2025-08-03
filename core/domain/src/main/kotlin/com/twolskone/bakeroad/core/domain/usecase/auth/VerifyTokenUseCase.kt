package com.twolskone.bakeroad.core.domain.usecase.auth

import com.twolskone.bakeroad.core.common.kotlin.extension.orFalse
import com.twolskone.bakeroad.core.domain.repository.AuthRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

/**
 * 토큰 유효성 검사
 */
class VerifyTokenUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(/*onError: (Throwable) -> Unit*/): Boolean =
        authRepository.verify()
            .map { true }
            /*.catch { cause ->
                onError(cause)
                emit(false)
            }*/.firstOrNull().orFalse()
}