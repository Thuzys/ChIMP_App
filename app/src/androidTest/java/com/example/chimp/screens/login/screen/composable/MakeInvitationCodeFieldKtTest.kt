package com.example.chimp.screens.login.screen.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class MakeInvitationCodeFieldKtTest{
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testMakeInvitationCodeField(){
        val testSubject = "init"
        val change = "change"
        var result = ""
        val expected = change+testSubject
        val testFunc:(String) -> Unit = { result = it }
        rule.setContent {
            MakeInvitationCodeField(
                value = testSubject,
                onInvitationCodeChange = testFunc
            )
        }
        rule.onNodeWithText(
            text = testSubject,
            useUnmergedTree = true
        )
            .assertExists()
        rule.onNodeWithText(
            text = testSubject,
            useUnmergedTree = true
        )
            .performTextInput(change)
        assert(result == expected)
    }
}