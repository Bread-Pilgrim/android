package com.twolskone.bakeroad.feature.review.write

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import timber.log.Timber

private const val MaxPickImages = 5

@Composable
internal fun WriteReviewRoute(
    modifier: Modifier = Modifier,
    viewModel: WriteReviewViewModel
) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = MaxPickImages)
    ) { uris ->
        Timber.i("xxxx uris: $uris")
    }

    WriteReviewScreen(
        modifier = modifier,
        onAddPhotoClick = {
            // Image picker does not require special permissions and can be activated right away.
            val mediaRequest = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            imagePickerLauncher.launch(mediaRequest)
        }
    )
}