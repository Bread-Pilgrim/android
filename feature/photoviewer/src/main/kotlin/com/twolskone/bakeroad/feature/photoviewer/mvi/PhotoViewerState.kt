package com.twolskone.bakeroad.feature.photoviewer.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class PhotoViewerState(
    val title: String = "",
    val description: String = "",
    val initialPage: Int = 0,
    val photoList: ImmutableList<String> = persistentListOf()
) : BaseUiState