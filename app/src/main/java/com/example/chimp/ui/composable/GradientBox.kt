package com.example.chimp.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

private val blue = Color(0xFF64B5F6)
private val lightBlue = Color(0xFFBBDEFB)

@Composable
fun GradientBox(
    colors: List<Color> = listOf(blue, lightBlue),
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
){
    Box(
        modifier =
        modifier
            .background(brush = Brush.linearGradient(colors))
    ) {
        content()
    }
}

@Preview
@Composable
private fun GradientBoxPreview() {
    GradientBox(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Hello, World!")
    }
}