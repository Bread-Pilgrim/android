package com.twolskone.bakeroad.feature.photoviewer

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.common.kotlin.extension.orZero
import com.twolskone.bakeroad.core.navigator.util.KEY_PHOTO_VIEWER_INITIAL_PAGE
import com.twolskone.bakeroad.core.navigator.util.KEY_PHOTO_VIEWER_PHOTO_ARRAY
import com.twolskone.bakeroad.core.navigator.util.KEY_PHOTO_VIEWER_TITLE
import com.twolskone.bakeroad.core.navigator.util.KEY_PHOTO_VIEWER_TITLE_ARRAY
import com.twolskone.bakeroad.feature.photoviewer.mvi.PhotoViewerIntent
import com.twolskone.bakeroad.feature.photoviewer.mvi.PhotoViewerSideEffect
import com.twolskone.bakeroad.feature.photoviewer.mvi.PhotoViewerState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList

@HiltViewModel
internal class PhotoViewerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<PhotoViewerState, PhotoViewerIntent, PhotoViewerSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): PhotoViewerState {
        val initialPage = savedStateHandle.get<Int>(KEY_PHOTO_VIEWER_INITIAL_PAGE).orZero()
        return PhotoViewerState(
            title = savedStateHandle.get<Array<String>>(KEY_PHOTO_VIEWER_TITLE_ARRAY)?.getOrNull(initialPage) ?: savedStateHandle.get<String>(KEY_PHOTO_VIEWER_TITLE).orEmpty(),
            initialPage = initialPage,
            photoList = savedStateHandle.get<Array<String>>(KEY_PHOTO_VIEWER_PHOTO_ARRAY).orEmpty().toImmutableList()
        )
    }

    private val titles = savedStateHandle.get<Array<String>>(KEY_PHOTO_VIEWER_TITLE_ARRAY).orEmpty()
    val initialPage = savedStateHandle.get<Int>(KEY_PHOTO_VIEWER_INITIAL_PAGE).orZero()

    override fun handleException(cause: Throwable) {}

    override suspend fun handleIntent(intent: PhotoViewerIntent) {
        when (intent) {
            is PhotoViewerIntent.OnPageChanged -> {
                if (titles.isNotEmpty()) {
                    reduce { copy(title = titles.getOrNull(intent.page).orEmpty()) }
                }
            }
        }
    }
}