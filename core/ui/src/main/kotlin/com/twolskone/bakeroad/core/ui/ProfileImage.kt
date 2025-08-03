package com.twolskone.bakeroad.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

/**
 * 사용자 프로필
 */
@Composable
fun ProfileImage(
    size: Dp = 32.dp,
    profileUrl: String
) {
    if (profileUrl.isNotBlank()) {
        AsyncImage(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = BakeRoadTheme.colorScheme.Gray100,
                    shape = CircleShape
                ),
            model = profileUrl,
            contentDescription = "Profile",
            placeholder = painterResource(id = R.drawable.core_ui_ic_person_image),
            contentScale = ContentScale.Crop
        )
    } else {
        Image(
            modifier = Modifier.size(size),
            imageVector = ImageVector.vectorResource(id = R.drawable.core_ui_ic_person_image),
            contentDescription = "Profile"
        )
    }
}