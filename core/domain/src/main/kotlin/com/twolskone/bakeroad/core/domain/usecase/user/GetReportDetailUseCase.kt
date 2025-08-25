package com.twolskone.bakeroad.core.domain.usecase.user

import com.twolskone.bakeroad.core.domain.repository.UserRepository
import com.twolskone.bakeroad.core.model.ReportDetail
import javax.inject.Inject
import kotlinx.coroutines.flow.first

/**
 * 빵말정산 조회
 */
class GetReportDetailUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(year: Int, month: Int): ReportDetail =
        userRepository.getReportDetail(year = year, month = month).first()
}