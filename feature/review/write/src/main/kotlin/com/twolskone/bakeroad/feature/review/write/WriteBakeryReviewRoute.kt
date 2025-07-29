package com.twolskone.bakeroad.feature.review.write

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.util.fastFilteredMap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.common.android.base.util.FileUtil
import com.twolskone.bakeroad.feature.review.write.mvi.WriteBakeryReviewIntent
import com.twolskone.bakeroad.feature.review.write.mvi.WriteBakeryReviewSideEffect
import timber.log.Timber

@Composable
internal fun WriteBakeryReviewRoute(
    modifier: Modifier = Modifier,
    viewModel: WriteReviewViewModel,
    onBackClick: () -> Unit,
    setResult: (code: Int, withFinish: Boolean) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val contentTextState = rememberTextFieldState(initialText = state.content)
    val availablePickImageCount = MaxPickImages - state.photoList.size
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = if (availablePickImageCount > 1) {
            ActivityResultContracts.PickMultipleVisualMedia(maxItems = availablePickImageCount)
        } else {
            ActivityResultContracts.PickVisualMedia()
        }
    ) { result ->
        Timber.i("xxxx result: $result")
        val uris = when (result) {
            is List<*> -> result.filterIsInstance<Uri>()  // Multiple Pick
            is Uri -> listOf(result)                      // Single Pick
            else -> emptyList()
        }
        val photos = uris.fastFilteredMap(
            predicate = { uri -> FileUtil.validateImageSizeFromUri(context, uri) },
            transform = { uri -> uri.toString() }
        )
        if (photos.isNotEmpty()) {
            viewModel.intent(WriteBakeryReviewIntent.AddPhotos(photos = photos))
        }
        if (photos.size < uris.size) {
            Toast.makeText(context, "20MB 미만의 사진만 업로드할 수 있어요.", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(contentTextState.text) {
        viewModel.intent(WriteBakeryReviewIntent.UpdateContent(content = contentTextState.text.toString()))
    }

    LaunchedEffect(viewModel) {
        viewModel.sideEffect.collect {
            when (it) {
                is WriteBakeryReviewSideEffect.SetResult -> setResult(it.code, it.withFinish)
            }
        }
    }

    WriteBakeryReviewScreen(
        modifier = modifier,
        state = state,
        onAddPhotoClick = {
            // Image picker does not require special permissions and can be activated right away.
            val mediaRequest = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            imagePickerLauncher.launch(mediaRequest)
        },
        onBackClick = onBackClick,
        onRatingChange = { rating -> viewModel.intent(WriteBakeryReviewIntent.ChangeRating(rating = rating)) },
        onDeletePhotoClick = { index -> viewModel.intent(WriteBakeryReviewIntent.DeletePhoto(index = index)) },
        onPrivateCheck = { checked -> viewModel.intent(WriteBakeryReviewIntent.CheckPrivate(checked = checked)) },
        onSubmit = { viewModel.intent(WriteBakeryReviewIntent.CompleteWrite) }
    )
}