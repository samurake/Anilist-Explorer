package com.anilist.aniexplorer.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun AnilistExplorerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val darkColorScheme = darkColorScheme(
        primary = AnilistColors.darkPrimary(),
        onPrimary = AnilistColors.navy(),
        primaryContainer = AnilistColors.navy(),
        onPrimaryContainer = AnilistColors.darkPrimary(),
        secondary = AnilistColors.darkSecondary(),
        onSecondary = AnilistColors.navy(),
        secondaryContainer = AnilistColors.navy(),
        onSecondaryContainer = AnilistColors.darkSecondary(),
        tertiary = AnilistColors.darkTertiary(),
        onTertiary = AnilistColors.appWhite(),
        tertiaryContainer = AnilistColors.darkTertiary(),
        onTertiaryContainer = AnilistColors.darkOnSurface(),
        background = AnilistColors.darkBackground(),
        onBackground = AnilistColors.darkOnBackground(),
        surface = AnilistColors.darkSurface(),
        onSurface = AnilistColors.darkOnSurface(),
        surfaceVariant = AnilistColors.darkSurface(),
        onSurfaceVariant = AnilistColors.darkOnSurface(),
    )

    val lightColorScheme = lightColorScheme(
        primary = AnilistColors.navy(),
        onPrimary = AnilistColors.appWhite(),
        primaryContainer = AnilistColors.lightBlue(),
        onPrimaryContainer = AnilistColors.navy(),
        secondary = AnilistColors.headerBlue(),
        onSecondary = AnilistColors.appWhite(),
        secondaryContainer = AnilistColors.lightBlue(),
        onSecondaryContainer = AnilistColors.headerBlue(),
        tertiary = AnilistColors.genreChipText(),
        onTertiary = AnilistColors.appWhite(),
        tertiaryContainer = AnilistColors.genreChipBackground(),
        onTertiaryContainer = AnilistColors.genreChipText(),
        background = AnilistColors.appWhite(),
        onBackground = AnilistColors.headerBlue(),
        surface = AnilistColors.appWhite(),
        onSurface = AnilistColors.headerBlue(),
        surfaceVariant = AnilistColors.backgroundGray(),
        onSurfaceVariant = AnilistColors.headerBlue(),
    )

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = anilistTypography(),
        content = content
    )
}
