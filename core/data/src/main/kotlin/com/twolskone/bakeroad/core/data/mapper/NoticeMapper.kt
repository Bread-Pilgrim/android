package com.twolskone.bakeroad.core.data.mapper

import com.twolskone.bakeroad.core.model.Notice
import com.twolskone.bakeroad.core.remote.model.notice.NoticeResponse

internal fun NoticeResponse.toExternalModel(): Notice =
    Notice(
        id = noticeId,
        title = noticeTitle,
        content = contents
            .filter { it.isNotEmpty() }
            .joinToString("\n") { content ->
                "• $content"
            }
    )