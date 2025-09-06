package com.twolskone.bakeroad.feature.badge

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.domain.usecase.badge.GetBadgesUseCase
import com.twolskone.bakeroad.core.domain.usecase.user.DisableBadgeUseCase
import com.twolskone.bakeroad.core.domain.usecase.user.EnableBadgeUseCase
import com.twolskone.bakeroad.core.eventbus.MainEventBus
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.core.model.Badge
import com.twolskone.bakeroad.feature.badge.mvi.BadgeListIntent
import com.twolskone.bakeroad.feature.badge.mvi.BadgeListSideEffect
import com.twolskone.bakeroad.feature.badge.mvi.BadgeListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import timber.log.Timber

@HiltViewModel
internal class BadgeListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mainEventBus: MainEventBus,
    getBadgesUseCase: GetBadgesUseCase,
    private val enableBadgeUseCase: EnableBadgeUseCase,
    private val disableBadgeUseCase: DisableBadgeUseCase
) : BaseViewModel<BadgeListState, BadgeListIntent, BadgeListSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): BadgeListState {
        return BadgeListState()
    }

    init {
        launch {
            val badges = getBadgesUseCase()
            reduce {
                copy(
                    representativeBadge = badges.find { it.isRepresentative } ?: Badge.ofEmpty(),
                    badgeList = badges.toImmutableList()
                )
            }
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

    override suspend fun handleIntent(intent: BadgeListIntent) {
        when (intent) {
            is BadgeListIntent.EnableBadge -> {
                enableBadgeUseCase(badgeId = intent.badge.id)
                reduce {
                    copy(
                        representativeBadge = intent.badge.copy(isRepresentative = true),
                        badgeList = badgeList.map { badge ->
                            badge.copy(isRepresentative = (badge.id == intent.badge.id))
                        }.toImmutableList()
                    )
                }
                showSnackbar(type = SnackbarType.SUCCESS, messageRes = R.string.feature_badge_snackbar_enable_badge)
                mainEventBus.setMyPageRefreshState(true)
            }

            is BadgeListIntent.DisableBadge -> {
                disableBadgeUseCase(badgeId = intent.badge.id)
                reduce {
                    copy(
                        representativeBadge = Badge.ofEmpty(),
                        badgeList = badgeList.map { badge ->
                            badge.copy(isRepresentative = false)
                        }.toImmutableList()
                    )
                }
                showSnackbar(type = SnackbarType.SUCCESS, messageRes = R.string.feature_badge_snackbar_disable_badge)
                mainEventBus.setMyPageRefreshState(true)
            }
        }
    }
}