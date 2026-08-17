package com.altomedia.divo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import com.altomedia.divo.R

val ArcadeGold: Color = Color(0xFFFFDE00)
val ArcadePink: Color = Color(0xFFF10AD4)
val PanelPurple: Color = Color(0xFF4B0870)

private val FruitDarkColors = darkColorScheme(
    primary = ArcadeGold,
    onPrimary = Color(0xFF4A071B),
    secondary = ArcadePink,
    onSecondary = Color.White,
    background = Color(0xFF240010),
    onBackground = Color.White,
    surface = PanelPurple,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF6C177C),
    onSurfaceVariant = Color(0xFFFFD8FA),
)

private val LuckiestGuy: FontFamily = FontFamily(
    Font(R.font.luckiest_guy, weight = FontWeight.Normal),
)

private val PtSerif: FontFamily = FontFamily(
    Font(R.font.pt_serif, weight = FontWeight.Normal),
)

private val FruitTypography: Typography = Typography(
    displaySmall = androidx.compose.ui.text.TextStyle(
        fontFamily = LuckiestGuy,
        fontWeight = FontWeight.Normal,
        lineBreak = LineBreak.Paragraph,
    ),
    headlineSmall = androidx.compose.ui.text.TextStyle(
        fontFamily = LuckiestGuy,
        fontWeight = FontWeight.Normal,
    ),
    titleLarge = androidx.compose.ui.text.TextStyle(
        fontFamily = LuckiestGuy,
        fontWeight = FontWeight.Normal,
    ),
    titleMedium = androidx.compose.ui.text.TextStyle(
        fontFamily = LuckiestGuy,
        fontWeight = FontWeight.Normal,
    ),
    bodyLarge = androidx.compose.ui.text.TextStyle(fontFamily = PtSerif),
    bodyMedium = androidx.compose.ui.text.TextStyle(fontFamily = PtSerif),
    labelLarge = androidx.compose.ui.text.TextStyle(
        fontFamily = LuckiestGuy,
        fontWeight = FontWeight.Normal,
    ),
    labelMedium = androidx.compose.ui.text.TextStyle(
        fontFamily = LuckiestGuy,
        fontWeight = FontWeight.Normal,
    ),
    labelSmall = androidx.compose.ui.text.TextStyle(
        fontFamily = LuckiestGuy,
        fontWeight = FontWeight.Normal,
    ),
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = FruitDarkColors,
        typography = FruitTypography,
        content = content,
    )
}
