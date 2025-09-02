package com.twolskone.bakeroad.core.domain.usecase.notice

import com.twolskone.bakeroad.core.domain.repository.NoticeRepository
import com.twolskone.bakeroad.core.model.Notice
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class GetNoticesUseCase @Inject constructor(private val noticeRepository: NoticeRepository) {

    suspend operator fun invoke(): List<Notice> =
        noticeRepository.getNotices().first()
}