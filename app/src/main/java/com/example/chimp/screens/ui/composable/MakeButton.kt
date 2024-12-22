package com.example.chimp.screens.ui.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun MakeButton(
    modifier: Modifier = Modifier,
    text : String,
    color: Color = MaterialTheme.colorScheme.primary,
    enable: Boolean = true,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enable,
        colors = ButtonDefaults.buttonColors(color),
        shape = RoundedCornerShape(ROUNDED_CORNER_RADIUS_ON_BUTTON.dp)
    ) {
        Text(text, style = buttonStyle)
    }
}

@Preview(showBackground = true)
@Composable
fun MakeButtonPreview() {
    MakeButton(text = "Button")
}