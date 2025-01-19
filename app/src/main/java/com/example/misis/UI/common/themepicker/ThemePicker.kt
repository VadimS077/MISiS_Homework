package com.example.misis.UI.common.themepicker

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.misis.UI.styleapp.DarkColorScheme
import com.example.misis.UI.styleapp.LightColorScheme
import com.example.misis.UI.styleapp.AppTypography

@Composable
fun ThemePicker(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
