package com.twolskone.bakeroad.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class BakeRoadColorScheme(
    val Primary50: Color,
    val Primary100: Color,
    val Primary200: Color,
    val Primary300: Color,
    val Primary400: Color,
    val Primary500: Color,
    val Primary600: Color,
    val Primary700: Color,
    val Primary800: Color,
    val Primary900: Color,
    val Primary950: Color,
    val Secondary50: Color,
    val Secondary100: Color,
    val Secondary200: Color,
    val Secondary300: Color,
    val Secondary400: Color,
    val Secondary500: Color,
    val Secondary600: Color,
    val Secondary700: Color,
    val Secondary800: Color,
    val Secondary900: Color,
    val Secondary950: Color,
    val Gray40: Color,
    val Gray50: Color,
    val Gray100: Color,
    val Gray200: Color,
    val Gray300: Color,
    val Gray400: Color,
    val Gray500: Color,
    val Gray600: Color,
    val Gray700: Color,
    val Gray800: Color,
    val Gray900: Color,
    val Gray950: Color,
    val Gray990: Color,
    val White: Color,
    val Black: Color,
    val Bg01: Color,
    val Opacity80: Color,
    val Opacity60: Color,
    val Opacity40: Color,
    val Opacity20: Color,
    val Opacity10: Color,
    val Opacity6: Color,
    val Success50: Color,
    val Success100: Color,
    val Success200: Color,
    val Success300: Color,
    val Success400: Color,
    val Success500: Color,
    val Success600: Color,
    val Success700: Color,
    val Success800: Color,
    val Success900: Color,
    val Success950: Color,
    val Error50: Color,
    val Error100: Color,
    val Error200: Color,
    val Error300: Color,
    val Error400: Color,
    val Error500: Color,
    val Error600: Color,
    val Error700: Color,
    val Error800: Color,
    val Error900: Color,
    val Error950: Color,
    val Positive50: Color,
    val Positive100: Color,
    val Positive200: Color,
    val Positive300: Color,
    val Positive400: Color,
    val Positive500: Color,
    val Positive600: Color,
    val Positive700: Color,
    val Positive800: Color,
    val Positive900: Color,
    val Positive950: Color,
)

internal val LocalBakeRoadColorScheme = staticCompositionLocalOf { bakeRoadLightColorScheme }
internal val bakeRoadLightColorScheme = BakeRoadColorScheme(
    Primary50 = Color(0xFFFFF4ED),
    Primary100 = Color(0xFFFFE7D4),
    Primary200 = Color(0xFFFFCBA8),
    Primary300 = Color(0xFFFFA670),
    Primary400 = Color(0xFFFF7537),
    Primary500 = Color(0xFFFF5E23),
    Primary600 = Color(0xFFF03506),
    Primary700 = Color(0xFFC72407),
    Primary800 = Color(0xFF9E1E0E),
    Primary900 = Color(0xFF711C0F),
    Primary950 = Color(0xFF450A05),
    Secondary50 = Color(0xFFEEF7FF),
    Secondary100 = Color(0xFFDAECFF),
    Secondary200 = Color(0xFFBDDEFF),
    Secondary300 = Color(0xFF90CBFF),
    Secondary400 = Color(0xFF6BB5FF),
    Secondary500 = Color(0xFF358BFC),
    Secondary600 = Color(0xFF1F6CF1),
    Secondary700 = Color(0xFF1755DE),
    Secondary800 = Color(0xFF1946B4),
    Secondary900 = Color(0xFF1A3E8E),
    Secondary950 = Color(0xFF152756),
    Gray40 = Color(0xFFFAFAFA),
    Gray50 = Color(0xFFF6F6F6),
    Gray100 = Color(0xFFE7E7E7),
    Gray200 = Color(0xFFD1D1D1),
    Gray300 = Color(0xFFB0B0B0),
    Gray400 = Color(0xFF888888),
    Gray500 = Color(0xFF6D6D6D),
    Gray600 = Color(0xFF595959),
    Gray700 = Color(0xFF4F4F4F),
    Gray800 = Color(0xFF454545),
    Gray900 = Color(0xFF3D3D3D),
    Gray950 = Color(0xFF262626),
    Gray990 = Color(0xFF121212),
    White = Color(0xFFFFFFFF),
    Black = Color(0xFF000000),
    Bg01 = Color(0xFFFFFBEE),
    Opacity80 = Color(0xCCB0B0B0),
    Opacity60 = Color(0x99B0B0B0),
    Opacity40 = Color(0x66B0B0B0),
    Opacity20 = Color(0x33B0B0B0),
    Opacity10 = Color(0x1AB0B0B0),
    Opacity6 = Color(0x0FB0B0B0),
    Success50 = Color(0xFFEEFFF2),
    Success100 = Color(0xFFD7F7E4),
    Success200 = Color(0xFFB2FCCA),
    Success300 = Color(0xFF76F2A2),
    Success400 = Color(0xFF33F573),
    Success500 = Color(0xFF09DE50),
    Success600 = Color(0xFF00BF40),
    Success700 = Color(0xFF049134),
    Success800 = Color(0xFF0A712E),
    Success900 = Color(0xFF0A5D28),
    Success950 = Color(0xFF003413),
    Error50 = Color(0xFFFFF1F1),
    Error100 = Color(0xFFFFDFDF),
    Error200 = Color(0xFFFFC5C5),
    Error300 = Color(0xFFFF9D9D),
    Error400 = Color(0xFFFF6464),
    Error500 = Color(0xFFFF2E2E),
    Error600 = Color(0xFFED1515),
    Error700 = Color(0xFFC80D0D),
    Error800 = Color(0xFFA50F0F),
    Error900 = Color(0xFF881414),
    Error950 = Color(0xFF4B0404),
    Positive50 = Color(0xFFE6F8FF),
    Positive100 = Color(0xFFDEFFFF),
    Positive200 = Color(0xFFB5E4FF),
    Positive300 = Color(0xFF83D5FF),
    Positive400 = Color(0xFF4BCBFF),
    Positive500 = Color(0xFF1E9AFF),
    Positive600 = Color(0xFF067AFF),
    Positive700 = Color(0xFF0066FF),
    Positive800 = Color(0xFF084EC5),
    Positive900 = Color(0xFF00469B),
    Positive950 = Color(0xFF0E2B5D),
)

// Primary
val Primary50 = Color(0xFFFFF4ED)
val Primary100 = Color(0xFFFFE7D4)
val Primary200 = Color(0xFFFFCBA8)
val Primary300 = Color(0xFFFFA670)
val Primary400 = Color(0xFFFF7537)
val Primary500 = Color(0xFFFF5E23)
val Primary600 = Color(0xFFF03506)
val Primary700 = Color(0xFFC72407)
val Primary800 = Color(0xFF9E1E0E)
val Primary900 = Color(0xFF711C0F)
val Primary950 = Color(0xFF450A05)

// Secondary
val Secondary50 = Color(0xFFEEF7FF)
val Secondary100 = Color(0xFFDAECFF)
val Secondary200 = Color(0xFFBDDEFF)
val Secondary300 = Color(0xFF90CBFF)
val Secondary400 = Color(0xFF6BB5FF)
val Secondary500 = Color(0xFF358BFC)
val Secondary600 = Color(0xFF1F6CF1)
val Secondary700 = Color(0xFF1755DE)
val Secondary800 = Color(0xFF1946B4)
val Secondary900 = Color(0xFF1A3E8E)
val Secondary950 = Color(0xFF152756)

// Gray
val Gray40 = Color(0xFFFAFAFA)
val Gray50 = Color(0xFFF6F6F6)
val Gray100 = Color(0xFFE7E7E7)
val Gray200 = Color(0xFFD1D1D1)
val Gray300 = Color(0xFFB0B0B0)
val Gray400 = Color(0xFF888888)
val Gray500 = Color(0xFF6D6D6D)
val Gray600 = Color(0xFF595959)
val Gray700 = Color(0xFF4F4F4F)
val Gray800 = Color(0xFF454545)
val Gray900 = Color(0xFF3D3D3D)
val Gray950 = Color(0xFF262626)
val Gray990 = Color(0xFF121212)

// Default
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)

// Background
val Bg01 = Color(0xFFFFFBEE)

// Opacity
val Opacity80 = Color(0xCCB0B0B0)
val Opacity60 = Color(0x99B0B0B0)
val Opacity40 = Color(0x66B0B0B0)
val Opacity20 = Color(0x33B0B0B0)
val Opacity10 = Color(0x1AB0B0B0)
val Opacity6 = Color(0x0FB0B0B0)

// Success
val Success50 = Color(0xFFEEFFF2)
val Success100 = Color(0xFFD7F7E4)
val Success200 = Color(0xFFB2FCCA)
val Success300 = Color(0xFF76F2A2)
val Success400 = Color(0xFF33F573)
val Success500 = Color(0xFF09DE50)
val Success600 = Color(0xFF00BF40)
val Success700 = Color(0xFF049134)
val Success800 = Color(0xFF0A712E)
val Success900 = Color(0xFF0A5D28)
val Success950 = Color(0xFF003413)

// Error
val Error50 = Color(0xFFFFF1F1)
val Error100 = Color(0xFFFFDFDF)
val Error200 = Color(0xFFFFC5C5)
val Error300 = Color(0xFFFF9D9D)
val Error400 = Color(0xFFFF6464)
val Error500 = Color(0xFFFF2E2E)
val Error600 = Color(0xFFED1515)
val Error700 = Color(0xFFC80D0D)
val Error800 = Color(0xFFA50F0F)
val Error900 = Color(0xFF881414)
val Error950 = Color(0xFF4B0404)

// Positive
val Positive50 = Color(0xFFE6F8FF)
val Positive100 = Color(0xFFDEFFFF)
val Positive200 = Color(0xFFB5E4FF)
val Positive300 = Color(0xFF83D5FF)
val Positive400 = Color(0xFF4BCBFF)
val Positive500 = Color(0xFF1E9AFF)
val Positive600 = Color(0xFF067AFF)
val Positive700 = Color(0xFF0066FF)
val Positive800 = Color(0xFF084EC5)
val Positive900 = Color(0xFF00469B)
val Positive950 = Color(0xFF0E2B5D)