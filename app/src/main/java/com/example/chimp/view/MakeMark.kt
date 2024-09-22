package com.example.chimp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun MakeMark(
    modifier: Modifier = Modifier,
    lightMode: Int,
    darkMode: Int,
    contendDescription: String
) {
    if (isSystemInDarkTheme()) {
        Image(
            painter = painterResource(darkMode),
            contentDescription = contendDescription,
            modifier = modifier
        )
    } else {
        Image(
            painter = painterResource(lightMode),
            contentDescription = contendDescription,
            modifier = modifier
        )
    }
}