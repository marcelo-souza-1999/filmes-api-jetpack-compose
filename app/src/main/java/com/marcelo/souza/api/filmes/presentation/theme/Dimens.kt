package com.marcelo.souza.api.filmes.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val size4: Dp = 4.dp,
    val size8: Dp = 8.dp,
    val size16: Dp = 16.dp,
    val size24: Dp = 24.dp,
    val size40: Dp = 40.dp,
    val size120: Dp = 120.dp,
    val size300: Dp = 300.dp
)

val LocalDimens = staticCompositionLocalOf { Dimens() }
