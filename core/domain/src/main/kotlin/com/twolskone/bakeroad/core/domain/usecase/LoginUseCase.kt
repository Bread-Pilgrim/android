package com.twolskone.bakeroad.core.domain.usecase

import com.twolskone.bakeroad.core.common.kotlin.network.extension.orFalse
import com.twolskone.bakeroad.core.domain.repository.AuthRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

/**
 * 로그인
 * - Kakao
 * TODO. 로그인 플랫폼 추가 시, enum 으로 타입 관리 및 로그인 로직 분기
 */
class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(accessToken: String, onError: (Throwable) -> Unit): Boolean =
        authRepository.login(accessToken)
            .map { true }
            .catch { cause ->
                onError(cause)
                emit(false)
            }.firstOrNull().orFalse()
}