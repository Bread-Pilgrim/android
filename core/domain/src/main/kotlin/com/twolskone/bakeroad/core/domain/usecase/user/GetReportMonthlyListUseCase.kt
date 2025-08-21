package com.twolskone.bakeroad.core.domain.usecase.user

import com.twolskone.bakeroad.core.domain.repository.UserRepository
import com.twolskone.bakeroad.core.model.ReportDate
import com.twolskone.bakeroad.core.model.paging.CursorPaging
import javax.inject.Inject
import kotlinx.coroutines.flow.first

/**
 * 빵말정산 월 목록 조회
 */
class GetReportMonthlyListUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(cursor: String): CursorPaging<ReportDate> =
        userRepository.getReportMonthlyList(cursor = cursor).first()
}