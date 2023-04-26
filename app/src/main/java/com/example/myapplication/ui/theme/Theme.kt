package com.example.myapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import javax.net.ssl.SSLEngineResult.Status

private val DarkColorPalette = darkColors(
    primary = Aqua500,
    primaryVariant = Peach200,
    secondary = Peach200
)

private val LightColorPalette = lightColors(
    primary = Aqua500,
    primaryVariant = Green200,
    secondary = Peach200,
    background = BackgroundColor,
    surface = BackgroundColor,

    /* Other default colors to override
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = LightColorPalette
    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(
        color = BackgroundColor
    )

    systemUiController.setNavigationBarColor(
        color = Peach200
    )

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}