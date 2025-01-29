package com.example.chimp.screens.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R

const val SEARCH_BAR_TAG = "SearchBar"

private const val CORNER_RADIUS = 50
private const val PADDING = 16

private val default_modifier =
    Modifier
        .fillMaxWidth()
        .padding(horizontal = PADDING.dp)
        .clip(RoundedCornerShape(CORNER_RADIUS.dp))

@Composable
fun SearchBar(
    modifier: Modifier = default_modifier,
    value: String = "",
    placeholderText : String = "Search...",
    iconTint: Color  = MaterialTheme.colorScheme.onPrimaryContainer,
    onValueChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = modifier
            .testTag(SEARCH_BAR_TAG),
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholderText) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_bar_content),
                tint = iconTint
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(CORNER_RADIUS.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
                keyboardController?.hide()
            }
        ),
    )
}

@Preview()
@Composable
private fun SearchBarPreview() {
    SearchBar()
}