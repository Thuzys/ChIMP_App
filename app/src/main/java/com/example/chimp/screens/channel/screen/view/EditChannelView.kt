package com.example.chimp.screens.channel.screen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Editing
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Loading
import com.example.chimp.screens.ui.composable.ImageSelector
import com.example.chimp.screens.ui.composable.MyHorizontalDivider

const val EDIT_CHANNEL_VIEW_TAG = "EditChannelView"

const val VERTICAL_SPACING = 16

@Composable
internal fun EditChannelView(
    modifier: Modifier = Modifier,
    state: Editing,
    onSave: (ChannelInfo) -> Unit,
    goBack: () -> Unit = {},
) {
    Column(
        modifier = modifier.testTag(EDIT_CHANNEL_VIEW_TAG),
        verticalArrangement = Arrangement.spacedBy(VERTICAL_SPACING.dp)
    ) {
        var description by remember { mutableStateOf(state.channel.description) }
        var icon by remember { mutableIntStateOf(state.channel.icon) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Edit Channel",
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.weight(0.7f))
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable(onClick = goBack)
                    .padding(8.dp),
                tint = MaterialTheme.colorScheme.error
            )
        }

        OutlinedTextField(
            value = description ?: "",
            onValueChange = { description = it },
            label = { Text("Channel Description") },
            modifier = Modifier.fillMaxWidth()
        )

        MyHorizontalDivider()

        Column {
            Text(
                text = "Select an icon:",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(8.dp)
            )

            ImageSelector(
                modifier = Modifier.fillMaxWidth(),
                image = icon,
                onImageSelected = { icon = it }
            )
        }

        MyHorizontalDivider()

        Button(
            onClick = { onSave(state.channel.copy(description = description, icon = icon)) },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
        ) {
            Text("Save")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditChannelViewPreview() {
    val channel = ChannelInfo(
        cId = 1u,
        name = ChannelName("Channel Name", "Channel Description"),
        description = "Channel Description",
        owner = UserInfo(1u, "Owner Name"),
    )
    EditChannelView(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        state = Editing(channel, Loading),
        onSave = {},
    )
}