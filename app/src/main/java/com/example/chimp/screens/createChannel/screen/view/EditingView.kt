package com.example.chimp.screens.createChannel.screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.models.channel.AccessControl
import com.example.chimp.models.channel.Visibility
import com.example.chimp.screens.createChannel.screen.composable.EditingBaseView
import com.example.chimp.screens.createChannel.viewModel.state.CreateChannelScreenState
import com.example.chimp.screens.ui.composable.ImageSelector
import com.example.chimp.screens.ui.composable.MakeButton
import com.example.chimp.screens.ui.composable.MySpacer
import com.example.chimp.screens.ui.composable.MyTextField
import com.example.chimp.screens.ui.composable.SelectOutlinedTextField

const val EDITING_VIEW_TAG = "EditingView"

const val ERROR_PADDING = 26

const val INPUT_FIELD_PADDING = 16

val visibilityOptions = listOf(
    "PUBLIC",
    "PRIVATE"
)

val accessControlOptions = listOf(
    "READ_ONLY",
    "READ_WRITE"
)

@Composable
internal fun EditingView(
    state: CreateChannelScreenState.Editing,
    showMessage: Boolean?,
    modifier: Modifier = Modifier,
    onSubmit: (String, Visibility, AccessControl) -> Unit = { _, _, _ -> },
    onChannelNameChange: (String) -> Unit = {},
) {
    val (channelName) = state
    val visibility = remember { mutableStateOf(Visibility.PUBLIC) }
    val accessControl = remember { mutableStateOf(AccessControl.READ_ONLY) }

    EditingBaseView(
        modifier = modifier.testTag(EDITING_VIEW_TAG)
    ){

        MySpacer()
        MyTextField(
            modifier = Modifier.padding(INPUT_FIELD_PADDING.dp),
            label = stringResource(R.string.channelName),
            value = channelName,
            onValueChange = onChannelNameChange
        )
        if (showMessage == true) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = stringResource(R.string.duplicate_channel_error),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding( start = ERROR_PADDING.dp)
                )
            }
        }
        SelectOutlinedTextField(
            options = visibilityOptions,
            selectedOption = visibility.value.name,
            onOptionSelected =
            {
                visibility.value = Visibility.valueOf(it)
            },
            label = stringResource(R.string.select_visibility),
            modifier = Modifier.padding(INPUT_FIELD_PADDING.dp)
        )
        MySpacer()
        SelectOutlinedTextField(
            options = accessControlOptions,
            selectedOption = accessControl.value.name,
            onOptionSelected =
            {
                accessControl.value = AccessControl.valueOf(it)
            },
            label = stringResource(R.string.select_accessControl),
            modifier = Modifier.padding(INPUT_FIELD_PADDING.dp)
        )

        ImageSelector(
            modifier = Modifier.padding(INPUT_FIELD_PADDING.dp),
            image = R.drawable.default_icon
        )
        MakeButton(
            modifier = Modifier.padding(INPUT_FIELD_PADDING.dp),
            text = stringResource(R.string.createChannel),
            onClick = {
                onSubmit(channelName, Visibility.PUBLIC, AccessControl.READ_ONLY)
            },
            enable = channelName.isNotBlank() && (showMessage == false)
        )
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun PreviewEditingView() {
    EditingView(CreateChannelScreenState.Editing(""), false)
}