package com.twolskone.bakeroad.feature.intro.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.intro.R

@Composable
internal fun LoginScreen(onKakaoLoginClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.feature_intro_ic_logo_login),
                contentDescription = "Logo"
            )
            Spacer(modifier = Modifier.height(104.dp))
            KakaoButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = onKakaoLoginClick
            )
        }
    }
}

private val Kakao = Color(0xFFFEE500)

@Composable
private fun KakaoButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(11.dp),
        colors = ButtonColors(
            containerColor = Kakao,
            contentColor = BakeRoadTheme.colorScheme.Gray990,
            disabledContainerColor = Kakao.copy(alpha = 0.4f),
            disabledContentColor = BakeRoadTheme.colorScheme.Gray300,
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.padding(end = 8.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.feature_intro_ic_kakao),
                contentDescription = "Kakao"
            )
            Text(
                text = stringResource(R.string.feature_intro_kakao_login),
                style = BakeRoadTheme.typography.bodyMediumSemibold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    BakeRoadTheme {
        LoginScreen(
            onKakaoLoginClick = {}
        )
    }
}
