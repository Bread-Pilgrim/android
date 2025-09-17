package com.twolskone.bakeroad.feature.photoviewer.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent

internal sealed interface PhotoViewerIntent : BaseUiIntent {
    data class OnPageChanged(val page: Int) : PhotoViewerIntent
}