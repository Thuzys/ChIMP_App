package com.example.chimp.screens.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

const val SELECT_OUTLINED_TEXT_FIELD_LABEL_TAG = "SelectOutlinedTextFieldLabel"

const val SELECT_OUTLINED_TEXT_FIELD_ARROW_DOWN_TAG = "SelectOutlinedTextFieldArrowDown"

@Composable
fun SelectOutlinedTextField(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var currentSelectedOption by remember { mutableStateOf(selectedOption) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = currentSelectedOption,
            onValueChange = {},
            label = {
                Text(
                    label,
                    modifier = Modifier.testTag(SELECT_OUTLINED_TEXT_FIELD_LABEL_TAG)
                )
            },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .testTag(SELECT_OUTLINED_TEXT_FIELD_ARROW_DOWN_TAG)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        currentSelectedOption = option
                        onOptionSelected(option)
                        expanded = false
                    },
                    text = { Text(option) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectOutlinedTextFieldPreview() {
    val options = listOf("Option 1", "Option 2", "Option 3")
    var selectedOption by remember { mutableStateOf(options[0]) }

    SelectOutlinedTextField(
        options = options,
        selectedOption = selectedOption,
        onOptionSelected = { selectedOption = it },
        label = "Select Option"
    )
}