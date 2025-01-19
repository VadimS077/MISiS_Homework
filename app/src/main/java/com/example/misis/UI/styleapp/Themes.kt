package com.example.misis.UI.styleapp

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

internal val LightColorScheme = lightColorScheme(
    background = GrayLight,                // Используем более светлый серый
    primary = White,                       // Белый остается без изменений
    onPrimary = Black,                     // Черный для текста на белом
    onSecondary = BlackA60,                // Черный с прозрачностью
    onTertiary = BlackA15,                 // Черный с меньшей прозрачностью
    surfaceContainerHigh = Red60,          // Новый мягкий красный
    surfaceContainerLow = GrayMedium,      // Средний серый
    outline = BlackA20,                    // Черный с небольшой прозрачностью
    outlineVariant = GrayDark,             // Темно-серый для границ
    onSurface = White,                     // Белый текст на поверхностях
    surface = Green50,                     // Мягкий зеленый
    surfaceVariant = Blue50,               // Холодный синий
    surfaceBright = Light,                 // Яркая тема
    surfaceDim = Dark,                     // Темная тема
    surfaceTint = CalendarColor,           // Цвет календаря
    onBackground = GrayMedium,             // Средний серый для фона
)

internal val DarkColorScheme = darkColorScheme(
    background = Black,                    // Черный фон
    primary = GrayCharcoal,                 // Очень темный серый для основного цвета
    onPrimary = White,                      // Белый текст на темном фоне
    onSecondary = WhiteA60,                 // Белый с прозрачностью для вторичных элементов
    onTertiary = WhiteA15,                  // Белый с меньшей прозрачностью
    surfaceContainerHigh = Red60,           // Мягкий красный для поверхности
    surfaceContainerLow = GrayDarker,       // Темный серый для низких поверхностей
    outline = WhiteA20,                    // Белый с низкой прозрачностью для границ
    outlineVariant = GrayDark,              // Тёмный серый для границ
    onSurface = White,                     // Белый текст на темных поверхностях
    surface = Green50,                     // Мягкий зеленый для поверхностей
    surfaceVariant = Blue50,                // Холодный синий для других поверхностей
    surfaceBright = Light,                  // Яркая тема для светлой версии
    surfaceDim = Dark,                     // Темная версия для тёмной темы
    surfaceTint = CalendarColorDark,        // Тёмный цвет для календаря
    onBackground = GrayDarker,              // Темный серый для текста на фоне
)
