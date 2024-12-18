package com.example.chimp.screens.register.screen.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chimp.models.either.Failure
import com.example.chimp.screens.register.model.InputValidation

/**
 * ShowErrorMessage is a composable function that displays an error message.
 *
 * @param message the message to display
 */
@Composable
internal fun ShowErrorMessage(message: InputValidation) {
    if (message is Failure<String>) {
        InputErrorDisplay(
            modifier = Modifier.fillMaxWidth(),
            message = message.value
        )
    }
}