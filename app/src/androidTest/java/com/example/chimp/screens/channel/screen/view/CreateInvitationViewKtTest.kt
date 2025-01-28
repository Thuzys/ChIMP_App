package com.example.chimp.screens.channel.screen.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test


class CreateInvitationViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun back_button_is_displayed_and_calls_onBackClick_function() {
        var backClicked = false
        rule.setContent {
            ChannelInvitationView(onBackClick = { backClicked = true })
        }

        rule.onNodeWithTag(CREATE_INVITATION_VIEW_BACK_BUTTON_TAG)
            .assertExists()
            .performClick()

        assert(backClicked)
    }

    @Test
    fun generate_invitation_button_is_displayed_and_calls_onGenerateClick_function() {
        var generateInvitationClicked = false
        rule.setContent {
            ChannelInvitationView(onGenerateClick = { generateInvitationClicked = true })
        }

        rule.onNodeWithTag(CREATE_INVITATION_VIEW_GENERATE_BUTTON_TAG)
            .assertExists()
            .performClick()

        assert(generateInvitationClicked)
    }
}