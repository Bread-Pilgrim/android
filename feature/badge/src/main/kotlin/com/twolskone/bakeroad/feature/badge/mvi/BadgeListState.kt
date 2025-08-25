package com.twolskone.bakeroad.feature.badge.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.Badge
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class BadgeListState(
    val representativeBadge: Badge = Badge.ofEmpty(),
    val badgeList: ImmutableList<Badge> = persistentListOf()
) : BaseUiState