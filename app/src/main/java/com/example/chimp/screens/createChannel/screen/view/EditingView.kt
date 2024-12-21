package com.example.chimp.screens.createChannel.screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.chimp.screens.ui.composable.MakeButton
import com.example.chimp.screens.ui.composable.MySpacer
import com.example.chimp.screens.ui.composable.MyTextField

const val EDITING_VIEW_TAG = "EditingView"

const val ERROR_PADDING = 26

const val INPUT_FIELD_PADDING = 16

@Composable
internal fun EditingView(
    state: CreateChannelScreenState.Editing,
    showMessage: Boolean?,
    modifier: Modifier = Modifier,
    onSubmit: (String, Visibility, AccessControl) -> Unit = { _, _, _ -> },
    onChannelNameChange: (String) -> Unit = {},
    onVisibilityChange: (Visibility) -> Unit = {},
    onAccessControlChange: (AccessControl) -> Unit = {}
) {
    EditingBaseView(
        modifier = modifier.testTag(EDITING_VIEW_TAG)
    ){
        val (channelName) = state

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