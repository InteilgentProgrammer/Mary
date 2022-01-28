package com.mary.lab.others

import androidx.compose.ui.graphics.Color
import com.mary.ui.theme.*

data class MaryTheme(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val secondaryVariant: Color,
    val background: Color,
    val surface: Color,
    val error: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onError: Color
)

val themeOne: MaryTheme = MaryTheme(
    primary = DarkGray,
    primaryVariant = Black,
    secondary = Gray,
    secondaryVariant = Gray,
    background = White,
    surface = Black,
    error = Red,
    onPrimary = White,
    onBackground = White,
    onError = White,
    onSecondary = Black,
    onSurface = White
)
val themeTwo: MaryTheme = MaryTheme(
    primary = DarkGray,
    primaryVariant = Black,
    secondary = Gray,
    secondaryVariant = Gray,
    background = White,
    surface = Black,
    error = Red,
    onPrimary = White,
    onBackground = White,
    onError = White,
    onSecondary = Black,
    onSurface = White
)
val theme = listOf(themeOne, themeTwo)

