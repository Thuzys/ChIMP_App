package com.example.chimp.screens.login.screen.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.chimp.screens.ui.composable.MY_TEXT_FIELD_TRAILING_ICON_TAG
import org.junit.Rule
import org.junit.Test

class MakePasswordTextFieldKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun isPasswordNotVisibleTest() {
        val password = "test"
        rule.setContent {
            MakePasswordTextField(
                value = password,
                isToShow = false
            )
        }
        rule.onNodeWithText(
            text = password,
            useUnmergedTree = true
        )
            .assertDoesNotExist()
    }

    @Test
    fun isPasswordVisibleTest() {
        val password = "test"
        rule.setContent {
            MakePasswordTextField(
                value = password,
                isToShow = true
            )
        }
        rule.onNodeWithText(
            text = password,
            useUnmergedTree = true
        )
            .assertExists()
    }

    @Test
    fun onPasswordChangeTest() {
        val testSubject = "init"
        val change = "change"
        var result = ""
        val expected = change+testSubject
        val testFunc: (String) -> Unit = { result = it }
        rule.setContent {
            MakePasswordTextField(
                value = testSubject,
                isToShow = true,
                onPasswordChange = testFunc
            )
        }
        rule.onNodeWithText(
            text = testSubject,
            useUnmergedTree = true
        )
            .performTextInput(change)
        assert(result == expected)
    }

    @Test
    fun isToShowChangeTest() {
        var result = false
        val text = "password"
        val onChange = { result = true }
        rule.setContent {
            MakePasswordTextField(
                value = text,
                isToShow = true,
                isToShowChange = onChange
            )
        }
        rule
            .onNodeWithTag(
                testTag = MY_TEXT_FIELD_TRAILING_ICON_TAG,
                useUnmergedTree = true
            )
            .performClick()
        assert(result)
    }
}