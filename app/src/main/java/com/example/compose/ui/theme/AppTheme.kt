package com.example.compose.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

data class AppColorScheme(
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val error: Color,
    val onError: Color,
    val isDark: Boolean
)

private val LocalAppColorScheme = staticCompositionLocalOf {
    AppColorScheme(
        primary = Color.Unspecified,
        secondary = Color.Unspecified,
        background = Color.Unspecified,
        surface = Color.Unspecified,
        surfaceVariant = Color.Unspecified,
        onPrimary = Color.Unspecified,
        onSecondary = Color.Unspecified,
        onBackground = Color.Unspecified,
        onSurface = Color.Unspecified,
        error = Color.Unspecified,
        onError = Color.Unspecified,
        isDark = false
    )
}

private val LocalAppTypography = staticCompositionLocalOf { AppTypography }

object AppTheme {
    val colorScheme: AppColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColorScheme.current

    val typography: androidx.compose.material3.Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        AppColorScheme(
            primary = DarkColors.primary,
            secondary = DarkColors.secondary,
            background = DarkColors.background,
            surface = DarkColors.surface,
            surfaceVariant = DarkColors.surfaceVariant,
            onPrimary = DarkColors.onPrimary,
            onSecondary = DarkColors.onSecondary,
            onBackground = DarkColors.onBackground,
            onSurface = DarkColors.onSurface,
            error = DarkColors.error,
            onError = DarkColors.onError,
            isDark = true
        )
    } else {
        AppColorScheme(
            primary = LightColors.primary,
            secondary = LightColors.secondary,
            background = LightColors.background,
            surface = LightColors.surface,
            surfaceVariant = LightColors.surfaceVariant,
            onPrimary = LightColors.onPrimary,
            onSecondary = LightColors.onSecondary,
            onBackground = LightColors.onBackground,
            onSurface = LightColors.onSurface,
            error = LightColors.error,
            onError = LightColors.onError,
            isDark = false
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides AppTypography,
        content = content
    )
}