package com.twolskone.bakeroad.feature.settings.notice

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.domain.usecase.notice.GetNoticesUseCase
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.feature.settings.notice.mvi.NoticeIntent
import com.twolskone.bakeroad.feature.settings.notice.mvi.NoticeSideEffect
import com.twolskone.bakeroad.feature.settings.notice.mvi.NoticeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import timber.log.Timber

@HiltViewModel
internal class NoticeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getNoticesUseCase: GetNoticesUseCase
) : BaseViewModel<NoticeState, NoticeIntent, NoticeSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): NoticeState {
        return NoticeState()
    }

    init {
        launch {
            val notices = getNoticesUseCase()
            reduce { copy(noticeList = notices.toImmutableList()) }
        }
    }

    override fun handleException(cause: Throwable) {
        Timber.e(cause)
        when (cause) {
            is ClientException -> {
                showSnackbar(
                    type = SnackbarType.ERROR,
                    message = cause.message,
                    messageRes = cause.error?.messageRes
                )
            }

            is BakeRoadException -> {
                showSnackbar(type = SnackbarType.ERROR, message = cause.message)
            }
        }
    }

    override suspend fun handleIntent(intent: NoticeIntent) {
        TODO("Not yet implemented")
    }
}