package util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppTheme {
    val darkColorPalette = darkColorScheme(
        tertiary = Color(0xffccdbe7),    // hashtag color
        tertiaryContainer = Color.DarkGray,
        onTertiaryContainer = Color(0xff0394f4),
        onBackground = Color(0xffe1e1e1)
    )

    val lightColorPalette = lightColorScheme(
        tertiary = Color(0xff00008b),    // hashtag color
        tertiaryContainer = Color.LightGray,
        onTertiaryContainer = Color.Blue,
        surfaceContainer = Color.White
    )
}

@Composable
fun getAppTheme(): ColorScheme {
    return if (isSystemInDarkTheme()) {
        AppTheme.darkColorPalette
    } else {
        AppTheme.lightColorPalette
    }
}