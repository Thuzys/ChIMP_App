package com.example.chimp.screens.ui.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import org.junit.Rule
import org.junit.Test

fun Modifier.backgroundColor(color: Color) = this.semantics {
    this[BackgroundColorKey] = color
}

val BackgroundColorKey = SemanticsPropertyKey<Color>("BackgroundColor")

fun SemanticsMatcher.Companion.backgroundColorIs(color: Color): SemanticsMatcher {
    return expectValue(BackgroundColorKey, color)
}

class MyHorizontalDividerKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun myHorizontalDivider_displaysInDarkTheme() {
        rule.setContent {
            MyHorizontalDivider(modifier = Modifier.testTag("MyHorizontalDivider").backgroundColor(Color.White.copy(alpha = 0.12f)))
        }

        rule.onNodeWithTag("MyHorizontalDivider")
            .assertIsDisplayed()
            .assert(SemanticsMatcher.backgroundColorIs(Color.White.copy(alpha = 0.12f)))
    }

    @Test
    fun myHorizontalDivider_displaysInLightTheme() {
        rule.setContent {
            MyHorizontalDivider(modifier = Modifier.testTag("MyHorizontalDivider").backgroundColor(Color.Black.copy(alpha = 0.12f)))
        }

        rule.onNodeWithTag("MyHorizontalDivider")
            .assertIsDisplayed()
            .assert(SemanticsMatcher.backgroundColorIs(Color.Black.copy(alpha = 0.12f)))
    }
}