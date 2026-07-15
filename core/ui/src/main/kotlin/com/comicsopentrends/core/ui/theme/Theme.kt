package com.comicsopentrends.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = VerseBlue,
    onPrimary = VerseWhite,
    secondary = VerseBlueDark,
    error = VerseError,
)

private val DarkColors = darkColorScheme(
    primary = VerseBlue,
    onPrimary = VerseBlack,
    secondary = VerseBlueDark,
    error = VerseError,
)

@Composable
fun ComicsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = ComicsTypography,
        content = content,
    )
}
