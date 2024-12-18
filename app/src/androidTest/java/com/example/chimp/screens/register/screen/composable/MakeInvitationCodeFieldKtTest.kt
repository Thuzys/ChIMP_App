package com.example.chimp.screens.register.screen.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class MakeInvitationCodeFieldKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun invitation_code_is_displayed() {
        val testSubject = "init"
        rule.setContent {
            MakeInvitationCodeField(value = testSubject)
        }
        rule
            .onNodeWithText(
                text = testSubject,
                useUnmergedTree = true
            ).assertExists()
    }

    @Test
    fun invitation_code_is_changed_correctly() {
        val testSubject = "init"
        val change = "new"
        var result = ""
        val expected = change+testSubject
        val onValueChange: (String) -> Unit = { result = it }
        rule.setContent {
            MakeInvitationCodeField(
                value = testSubject,
                onInvitationCodeChange = onValueChange
            )
        }
        rule
            .onNodeWithText(
                text = testSubject,
                useUnmergedTree = true
            ).performTextInput(change)
        assert(result == expected)
    }
}