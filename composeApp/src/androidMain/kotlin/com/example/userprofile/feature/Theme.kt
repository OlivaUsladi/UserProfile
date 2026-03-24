package com.example.userprofile.feature

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


val LightGradientBrush = Brush.linearGradient(
    colors = listOf(Color(0xFF2670CC), Color(0xFF26CCAD))
)

val DarkGradientBrush = Brush.linearGradient(
    colors = listOf(Color(0xFF1A1A1A), Color(0xFF2D2D2D), Color(0xFF1F1F1F))
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2670CC),
    secondary = Color(0xFF26CCAD),
    tertiary = Color(0xFFF5F5F5),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF8F9FA),
    surfaceVariant = Color(0xFFE8F0FE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF1A1A1A),
    onSurface = Color(0xFF1A1A1A),
    onSurfaceVariant = Color(0xFF2670CC)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF3B8BEA),
    secondary = Color(0xFF3AE0C0),
    tertiary = Color(0xFF2C2C2C),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    surfaceVariant = Color(0xFF2A2A2A),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color(0xFFE0E0E0),
    onSurface = Color(0xFFE0E0E0),
    onSurfaceVariant = Color(0xFF3B8BEA)
)

@Composable
fun UserProfileTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as android.app.Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}

@Composable
fun getGradientBrush(darkTheme: Boolean): Brush {
    return if (darkTheme) DarkGradientBrush else LightGradientBrush
}