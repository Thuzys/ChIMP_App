package com.example.chimp.screens.createChannel.screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.models.channel.AccessControl
import com.example.chimp.models.channel.Visibility
import com.example.chimp.screens.createChannel.model.ChannelInput
import com.example.chimp.screens.createChannel.screen.composable.EditingBaseView
import com.example.chimp.screens.createChannel.viewModel.state.CreateChannelScreenState
import com.example.chimp.screens.ui.composable.ImageSelector
import com.example.chimp.screens.ui.composable.MakeButton
import com.example.chimp.screens.ui.composable.MySpacer
import com.example.chimp.screens.ui.composable.MyTextField
import com.example.chimp.screens.ui.composable.SelectOutlinedTextField

private const val ERROR_PADDING = 26
private const val INPUT_FIELD_PADDING = 16
private const val TOP = 60


@Composable
internal fun EditingView(
    state: CreateChannelScreenState.Editing,
    modifier: Modifier = Modifier,
    onSubmit: (ChannelInput) -> Unit = { _ -> },
    onChannelNameChange: (String) -> Unit = {},
) {
    val visibilityOptions = listOf(
        stringResource(R.string.PUBLIC),
        stringResource(R.string.PRIVATE)
    )
    val accessControlOptions = listOf(
        stringResource(R.string.READ_ONLY),
        stringResource(R.string.READ_WRITE)
    )
    val channelName = state.channelName
    val visibility = remember { mutableStateOf(Visibility.PUBLIC) }
    val accessControl = remember { mutableStateOf(AccessControl.READ_ONLY) }
    val description = remember { mutableStateOf("") }
    var icon by remember { mutableIntStateOf(R.drawable.default_icon) }

     EditingBaseView(
         modifier = modifier,
     ) {
        MySpacer()
        MyTextField(
            modifier = Modifier.padding(
                top = TOP.dp,
                start = INPUT_FIELD_PADDING.dp,
                bottom = INPUT_FIELD_PADDING.dp,
                end = INPUT_FIELD_PADDING.dp,
                ),
            label = stringResource(R.string.channelName),
            value = channelName.input,
            onValueChange = onChannelNameChange
        )
        if (!channelName.isValid && channelName.input.isNotBlank()) {
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
        MyTextField(
            value = description.value,
            onValueChange = {description.value = it},
            label = stringResource(R.string.channel_description),
            modifier = Modifier.padding(INPUT_FIELD_PADDING.dp)
        )
        ImageSelector(
            modifier = Modifier.padding(INPUT_FIELD_PADDING.dp),
            image = icon,
            onImageSelected = { icon = it }
        )
        MakeButton(
            modifier = Modifier.padding(INPUT_FIELD_PADDING.dp),
            text = stringResource(R.string.createChannel),
            onClick = {
                onSubmit(ChannelInput(
                    channelName = channelName.input,
                    visibility = visibility.value,
                    accessControl = accessControl.value,
                    description = description.value,
                    icon = icon
                ))
            },
            enable = channelName.input.isNotBlank() && channelName.isValid
        )
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun PreviewEditingView() {
    EditingView(CreateChannelScreenState.Editing())
}