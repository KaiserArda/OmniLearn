package com.example.medquiz.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Mapping our custom colors to Material Theme slots
private val LightColorScheme = lightColorScheme(
    primary = Primary,           // Brand color
    onPrimary = OnPrimary,       // Text on buttons

    background = LightGray,      // Screen bg
    onBackground = TextPrimary,  // Main text

    surface = White,             // Cards
    onSurface = TextPrimary      // Text on cards
)

@Composable
fun MedQuizTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Set status bar to brand blue
            window.statusBarColor = colorScheme.primary.toArgb()
            // White icons on status bar
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}