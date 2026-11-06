package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SaffronLight,
    onPrimary = Color.Black,
    primaryContainer = SaffronDark,
    onPrimaryContainer = GoldenLight,
    secondary = GoldenAmber,
    onSecondary = Color.Black,
    background = DarkBg,
    surface = DarkSurface,
    onBackground = Color(0xFFF5EBE1),
    onSurface = Color(0xFFF5EBE1),
    surfaceVariant = Color(0xFF2E241E),
    onSurfaceVariant = Color(0xFFD6C7B8)
)

private val LightColorScheme = lightColorScheme(
    primary = SaffronPrimary,
    onPrimary = Color.White,
    primaryContainer = GoldenLight,
    onPrimaryContainer = SaffronDark,
    secondary = MaroonPrimary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFCE7F3),
    onSecondaryContainer = MaroonDark,
    background = CreamBackground,
    surface = CreamSurface,
    onBackground = Color(0xFF1C130E),
    onSurface = Color(0xFF1C130E),
    surfaceVariant = Color(0xFFF5EBE1),
    onSurfaceVariant = Color(0xFF574438),
    outline = Color(0xFFD6C4B2)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
