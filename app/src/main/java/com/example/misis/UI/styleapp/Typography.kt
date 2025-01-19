package com.example.misis.UI.styleapp

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

val DefaultFontFamily = FontFamily.SansSerif

val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = DefaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,  //
        lineHeight = 28.sp,  //
        letterSpacing = 0.6.sp,
        color = Color(0xFF212121)  //
    ),
    titleLarge = TextStyle(
        fontFamily = DefaultFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,  //
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
        color = Color(0xFFFF5722)  // Яркий оранжевый цвет для заголовков
    ),
    displayMedium = TextStyle(
        fontFamily = DefaultFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,  // Размер подзаголовков
        lineHeight = 38.sp,
        letterSpacing = 0.5.sp,
        color = Color(0xFF607D8B)  // Спокойный синий цвет
    ),
    titleSmall = TextStyle(
        fontFamily = DefaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,  // Маленький размер для подзаголовков
        letterSpacing = 0.3.sp,
        color = Color(0xFF9E9E9E)  // Светло-серый для вторичных заголовков
    )
)
