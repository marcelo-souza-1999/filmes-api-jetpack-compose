package com.marcelo.souza.api.filmes.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

data class AppColors(
    val accent: Color,
    val accentOn: Color,
    val error: Color,
    val border: Color,
    val placeholder: Color,
    val ratingBackground: Color,
    val watchButtonBackground: Color,
    val watchButtonContent: Color
)

private val DefaultLightAppColors = AppColors(
    accent = Red,
    accentOn = White,
    error = Red,
    border = Gray,
    placeholder = Gray,
    ratingBackground = Gray,
    watchButtonBackground = Color.Transparent,
    watchButtonContent = Black100
)

private val DefaultDarkAppColors = AppColors(
    accent = Red,
    accentOn = White,
    error = Red,
    border = Color(0xFF2A2A2A),
    placeholder = Color(0xFF3A3A3A),
    ratingBackground = Color(0xFF2F2F2F),
    watchButtonBackground = Color.Transparent,
    watchButtonContent = White
)

internal val LocalAppColors = staticCompositionLocalOf { DefaultLightAppColors }

private val LightColorScheme = lightColorScheme(
    primary = Red,
    secondary = Gray,
    tertiary = White,
    background = White,
    surface = GrayLight,
    onPrimary = White,
    onSecondary = Black100,
    onBackground = Black100,
    onSurface = Black100
)

private val DarkColorScheme = darkColorScheme(
    primary = Red,
    secondary = Gray,
    tertiary = White,
    background = Black100,
    surface = Black50,
    onPrimary = White,
    onSecondary = White,
    onBackground = White,
    onSurface = White
)

@Composable
fun ApiMoviesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val appColors = if (darkTheme) {
        DefaultDarkAppColors
    } else {
        DefaultLightAppColors
    }

    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}