package com.twolskone.bakeroad.feature.settings.notice.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.Notice
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class NoticeState(
    val noticeList: ImmutableList<Notice> = persistentListOf()
) : BaseUiState