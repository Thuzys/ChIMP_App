package com.example.chimp.screens.ui.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * MenuBottomBar is a composable that represents the bottom bar of the app.
 *
 * @param modifier Modifier The modifier to be applied to the layout.
 * @param chatsIsEnable Boolean The state of the menu button.
 * @param findChannelIsEnable Boolean The state of the find channel button.
 * @param aboutIsEnable Boolean The state of the about button.
 * @param onMenuClick () -> Unit The action to be performed when the menu button is clicked.
 * @param findChannelClick () -> Unit The action to be performed when the find channel button is clicked.
 * when the add channel button is clicked.
 * @param aboutClick () -> Unit The action to be performed when the about button is clicked.
 */
@Composable
@Preview
fun MenuBottomBar(
    modifier: Modifier = Modifier,
    chatsIsEnable: Boolean = true,
    findChannelIsEnable: Boolean = true,
    aboutIsEnable: Boolean = true,
    createChannelIsEnable: Boolean = true,
    onMenuClick: () -> Unit = {},
    findChannelClick: () -> Unit = {},
    aboutClick: () -> Unit = {},
    createChannelClick: () -> Unit = {}
) {
    BottomAppBar(
        contentPadding = PaddingValues(16.dp),
    ) {
        IconButton(
            onClick = onMenuClick,
            enabled = chatsIsEnable
        ) {
            Icon(Icons.Default.Home, contentDescription = "Menu")
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = findChannelClick,
            enabled = findChannelIsEnable
        ) {
            Icon(Icons.Default.AddCircle, contentDescription = "AddChannel")
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = aboutClick,
            enabled = aboutIsEnable
        ) {
            Icon(Icons.Default.Info, contentDescription = "About")
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = createChannelClick,
            enabled = createChannelIsEnable
        ) {
            Icon(Icons.Default.Add, contentDescription = "CreateChannel")
        }
    }
}