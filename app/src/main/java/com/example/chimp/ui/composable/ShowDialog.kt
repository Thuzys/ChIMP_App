package com.example.chimp.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

/**
 * The alpha value used for the dialog.
 */
private const val ALPHA_VALUE = 0.5f

/**
 * The padding used for the dialog.
 */
private const val DIALOG_PADDING = 16

/**
 * The composable function that displays the dialog.
 * @param showDialog [Boolean] The state of the dialog.
 * @param content [@Composable ColumnScope.() -> Unit] The content of the dialog.
 */
@Composable
fun ShowDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    var showDialog1 = showDialog
    if (showDialog1) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color.Black.copy(alpha = ALPHA_VALUE),
                        shape = MaterialTheme.shapes.extraLarge
                    )
                    .padding(DIALOG_PADDING.dp)
                    .clickable { showDialog1 = !showDialog1 },
                contentAlignment = Alignment.Center
            ) {
                Column (
                    content = content
                )
            }
        }
    }
}

//TODO: Add a preview for the ShowDialog composable function.