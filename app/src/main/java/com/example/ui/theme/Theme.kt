package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = KashmirMint,
    onPrimary = KashmirDarkSurface,
    primaryContainer = KashmirEmerald,
    onPrimaryContainer = KashmirLightGreen,
    secondary = SaffronAmber,
    onSecondary = Color.Black,
    secondaryContainer = SaffronOrange,
    onSecondaryContainer = SaffronLight,
    tertiary = InfoBlue,
    background = Color(0xFF0F1713),
    surface = Color(0xFF16231C),
    onBackground = Color(0xFFE5E7EB),
    onSurface = Color(0xFFE5E7EB),
    outline = Color(0xFF2E4035)
)

private val LightColorScheme = lightColorScheme(
    primary = KashmirForestGreen,
    onPrimary = Color.White,
    primaryContainer = KashmirLightGreen,
    onPrimaryContainer = KashmirForestGreen,
    secondary = SaffronAmber,
    onSecondary = Color.White,
    secondaryContainer = SaffronLight,
    onSecondaryContainer = SaffronAmber,
    tertiary = InfoBlue,
    background = EarthBackground,
    surface = EarthCardSurface,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    outline = EarthBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set false to ensure Jehlum Sense AI brand colors shine through
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
