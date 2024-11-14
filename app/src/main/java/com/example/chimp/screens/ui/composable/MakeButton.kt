package com.example.chimp.screens.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * The radius of the rounded corners of the box.
 */
private const val ROUNDED_CORNER_RADIUS_ON_BUTTON = 10

/**
 * The style of the button text.
 */
private val buttonStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight(500))

/**
 * The color of the button.
 */
private val buttonColors = Color(0xFF64B5F6)

@Composable
fun MakeButton(
    modifier: Modifier = Modifier,
    text : String,
    enable: Boolean = true,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enable,
        shape = RoundedCornerShape(ROUNDED_CORNER_RADIUS_ON_BUTTON.dp)
    ) {
        Text(text, style = buttonStyle)
    }
}