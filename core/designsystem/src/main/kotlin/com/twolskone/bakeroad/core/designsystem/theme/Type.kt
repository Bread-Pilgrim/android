package com.twolskone.bakeroad.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.twolskone.bakeroad.core.designsystem.R

private val pretendard = FontFamily(
    Font(resId = R.font.pretendard_bold, weight = FontWeight(700)),
    Font(resId = R.font.pretendard_semibold, weight = FontWeight(600)),
    Font(resId = R.font.pretendard_medium, weight = FontWeight(500)),
    Font(resId = R.font.pretendard_regular, weight = FontWeight(400))
)

@Immutable
data class BakeRoadTypography(
    val headingLargeBold: TextStyle,
    val headingMediumBold: TextStyle,
    val headingSmallBold: TextStyle,
    val bodyXLargeSemibold: TextStyle,
    val bodyLargeSemibold: TextStyle,
    val bodyMediumSemibold: TextStyle,
    val bodySmallSemibold: TextStyle,
    val bodyXSmallSemibold: TextStyle,
    val body2XSmallSemibold: TextStyle,
    val bodyXLargeMedium: TextStyle,
    val bodyLargeMedium: TextStyle,
    val bodyMediumMedium: TextStyle,
    val bodySmallMedium: TextStyle,
    val bodyXSmallMedium: TextStyle,
    val body2XSmallMedium: TextStyle,
    val bodyXLargeRegular: TextStyle,
    val bodyLargeRegular: TextStyle,
    val bodyMediumRegular: TextStyle,
    val bodySmallRegular: TextStyle,
    val bodyXSmallRegular: TextStyle,
    val body2XSmallRegular: TextStyle,
)

internal val LocalBakeRoadTypography = staticCompositionLocalOf { bakeRoadTypography }
internal val bakeRoadTypography = BakeRoadTypography(
    headingLargeBold = TextStyle(
        fontFamily = pretendard,
        fontSize = 22.sp,
        fontWeight = FontWeight(700),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    headingMediumBold = TextStyle(
        fontFamily = pretendard,
        fontSize = 20.sp,
        fontWeight = FontWeight(700),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    headingSmallBold = TextStyle(
        fontFamily = pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight(700),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    bodyXLargeSemibold = TextStyle(
        fontFamily = pretendard,
        fontSize = 20.sp,
        fontWeight = FontWeight(600),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    bodyLargeSemibold = TextStyle(
        fontFamily = pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight(600),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    bodyMediumSemibold = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight(600),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    bodySmallSemibold = TextStyle(
        fontFamily = pretendard,
        fontSize = 15.sp,
        fontWeight = FontWeight(600),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    bodyXSmallSemibold = TextStyle(
        fontFamily = pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight(600),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    body2XSmallSemibold = TextStyle(
        fontFamily = pretendard,
        fontSize = 13.sp,
        fontWeight = FontWeight(600),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    bodyXLargeMedium = TextStyle(
        fontFamily = pretendard,
        fontSize = 20.sp,
        fontWeight = FontWeight(500),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    bodyLargeMedium = TextStyle(
        fontFamily = pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight(500),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    bodyMediumMedium = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight(500),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    bodySmallMedium = TextStyle(
        fontFamily = pretendard,
        fontSize = 15.sp,
        fontWeight = FontWeight(500),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    bodyXSmallMedium = TextStyle(
        fontFamily = pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight(500),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    body2XSmallMedium = TextStyle(
        fontFamily = pretendard,
        fontSize = 13.sp,
        fontWeight = FontWeight(500),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    bodyXLargeRegular = TextStyle(
        fontFamily = pretendard,
        fontSize = 20.sp,
        fontWeight = FontWeight(400),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    bodyLargeRegular = TextStyle(
        fontFamily = pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight(400),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    bodyMediumRegular = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight(400),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    bodySmallRegular = TextStyle(
        fontFamily = pretendard,
        fontSize = 15.sp,
        fontWeight = FontWeight(400),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    bodyXSmallRegular = TextStyle(
        fontFamily = pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight(400),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    ),
    body2XSmallRegular = TextStyle(
        fontFamily = pretendard,
        fontSize = 13.sp,
        fontWeight = FontWeight(400),
        lineHeight = 1.4.em,
        letterSpacing = 0.sp
    )
)