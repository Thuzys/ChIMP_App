package com.example.chimp.screens.channel.screen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chimp.R
import com.example.chimp.models.channel.ChannelInvitation
import com.example.chimp.models.time.getMillisFromTimeString
import com.example.chimp.models.time.toOptionFormat
import com.example.chimp.screens.channel.model.accessControl.AccessControl
import com.example.chimp.screens.ui.composable.SelectOutlinedTextField
import java.sql.Timestamp

const val CREATE_INVITATION_VIEW_TAG = "CreateInvitationView"

const val CREATE_INVITATION_VIEW_BACK_BUTTON_TAG = "CreateInvitationViewBackButton"

const val CREATE_INVITATION_VIEW_GENERATE_BUTTON_TAG = "CreateInvitationViewGenerateButton"

const val PADDING = 16
const val SPACING = 16
const val FONT_SIZE = 20

@Composable
internal fun ChannelInvitationView(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onGenerateClick: (ChannelInvitation) -> Unit = { },
) {
    val channelInvitation = remember { mutableStateOf(ChannelInvitation.createDefault()) }

    Column(
        modifier = modifier
            .testTag(CREATE_INVITATION_VIEW_TAG)
            .padding(PADDING.dp),
        verticalArrangement = Arrangement.spacedBy(SPACING.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.channel_invitation),
                fontSize = FONT_SIZE.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                modifier = Modifier
                    .clickable(onClick = onBackClick)
                    .testTag(CREATE_INVITATION_VIEW_BACK_BUTTON_TAG),
                imageVector = Icons.Default.Close,
                contentDescription = "Back",
            )
        }
        SelectOutlinedTextField(
            options = listOf(
                stringResource(R.string.minutes_30),
                stringResource(R.string.hour_1),
                stringResource(R.string.day_1),
                stringResource(R.string.week_1),
            ),
            selectedOption = channelInvitation.value.expiresAfter.toOptionFormat(),
            onOptionSelected =
            {
                channelInvitation.value =
                    channelInvitation.value.copy(expiresAfter = Timestamp(getMillisFromTimeString(it)))
            },
            label = stringResource(R.string.expires_after),
            modifier = Modifier.fillMaxWidth()
        )

        SelectOutlinedTextField(
            options = listOf("1", "2", "3", "4", "5", "10", "20", "50", "100"),
            selectedOption = channelInvitation.value.maxUses.toString(),
            onOptionSelected = { maxUses ->
                maxUses.toUIntOrNull()
                    ?.let { channelInvitation.value = channelInvitation.value.copy(maxUses = it) }
            },
            label = stringResource(R.string.max_number_of_uses),
            modifier = Modifier.fillMaxWidth()
        )

        SelectOutlinedTextField(
            options = listOf(
                stringResource(R.string.READ_ONLY),
                stringResource(R.string.READ_WRITE),
            ),
            selectedOption = channelInvitation.value.permission.name,
            onOptionSelected = {
                channelInvitation.value =
                    channelInvitation.value.copy(permission = AccessControl.valueOf(it))
            },
            label = stringResource(R.string.permissions),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.crate_channel_invitation_warning),
            color = Color.Red
        )
        Text(
            text = stringResource(R.string.create_channel_invitation_suggestion),
        )
        Button(
            onClick = { onGenerateClick(channelInvitation.value) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(CREATE_INVITATION_VIEW_GENERATE_BUTTON_TAG)
        ) {
            Text(stringResource(R.string.generate_invitation_code))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChannelInvitationViewPreview() {
    ChannelInvitationView(onGenerateClick = {})
}