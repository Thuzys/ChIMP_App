package com.example.chimp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

/**
 * The composable function that displays the mark.
 * @param modifier [Modifier] The modifier to be applied to the layout.
 * @param lightMode [Int] The light mode image resource id.
 * @param darkMode [Int] The dark mode image resource id.
 * @param contendDescription [String] The content description of the image.
 */
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