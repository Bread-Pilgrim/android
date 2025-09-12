package com.twolskone.bakeroad

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.domain.usecase.user.DeleteAllDataStoresUseCase
import com.twolskone.bakeroad.core.eventbus.MainEventBus
import com.twolskone.bakeroad.core.model.Badge
import com.twolskone.bakeroad.core.navigator.util.KEY_BADGE_ACHIEVED
import com.twolskone.bakeroad.mvi.MainDialogState
import com.twolskone.bakeroad.mvi.MainIntent
import com.twolskone.bakeroad.mvi.MainSideEffect
import com.twolskone.bakeroad.mvi.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val mainEventBus: MainEventBus,
    val deleteAllDataStoresUseCase: DeleteAllDataStoresUseCase
) : BaseViewModel<MainState, MainIntent, MainSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): MainState {
        return MainState()
    }

    // 획득한 뱃지
    val achieveBadges = savedStateHandle.getStateFlow(KEY_BADGE_ACHIEVED, emptyList<Badge>())

    override fun handleException(cause: Throwable) {}

    override suspend fun handleIntent(intent: MainIntent) {
        when (intent) {
            MainIntent.RefreshHome -> {
                mainEventBus.setHomeRefreshState(value = true)
                postSideEffect(MainSideEffect.NavigateToHome)
            }

            is MainIntent.AchieveBadges -> {
                savedStateHandle[KEY_BADGE_ACHIEVED] = intent.badges
            }

            MainIntent.ShowTokenExpiredAlert -> reduce { copy(dialog = MainDialogState.TokenExpired) }

            MainIntent.HandleTokenExpired -> {
                reduce { copy(dialog = MainDialogState.None) }
                deleteAllDataStoresUseCase()
                postSideEffect(MainSideEffect.NavigateToLogin)
            }
        }
    }
}