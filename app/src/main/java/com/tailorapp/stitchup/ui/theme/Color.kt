package com.tailorapp.stitchup.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Gray = Color(0xFF363A3C)
val Blue = Color(0xFF07AEB4)
val SoftGolden = Color(0xFFD6C9B9)
val SoftMediumGolden = Color(0xFFC7B9AE)
val Brown = Color(0xFF302115)
val MediumBrown = Color(0xFF765232)
val LightBrown = Color(0xFFBA8E5F)
val DarkBrown = Color(0xFF322213)
val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val LightGray = Color(0xFFCCCACA)

val ColorScheme.focusedTextFieldText
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White else Color.Black


val ColorScheme.unfocusedTextFieldText
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF94A3B8) else Color(0xFF475569)

val ColorScheme.textFieldContainer
    @Composable
    get() = if (isSystemInDarkTheme()) Gray.copy(alpha = 0.6f) else LightGray