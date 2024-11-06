package com.example.chimp.ui.composable

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun HyperlinkText(
    text: String,
    url: String,
) {
    val context = LocalContext.current
    Text(
        text = AnnotatedString(
            text = text,
            spanStyle = SpanStyle(
                textDecoration = TextDecoration.Underline,
                color = Color.Black
            ),
        ),
        style = TextStyle(
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
        ),
        modifier = Modifier.clickable {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        },
    )
}