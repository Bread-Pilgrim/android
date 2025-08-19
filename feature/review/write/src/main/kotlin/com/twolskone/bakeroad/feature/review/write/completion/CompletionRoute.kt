package com.twolskone.bakeroad.feature.review.write.completion

import androidx.compose.runtime.Composable

@Composable
internal fun CompletionRoute(
    goHome: () -> Unit,
    confirmReview: () -> Unit
) {
    CompletionScreen(
        onGoHomeClick = goHome,
        onConfirmReviewClick = confirmReview
    )
}