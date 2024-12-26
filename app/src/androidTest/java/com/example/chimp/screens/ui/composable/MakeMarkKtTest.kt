package com.example.chimp.screens.ui.composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.semantics
import org.junit.Rule
import org.junit.Test
import com.example.chimp.R

fun Modifier.drawableResource(resourceId: Int) = this.semantics {
    this[DrawableResourceKey] = resourceId
}

val DrawableResourceKey = SemanticsPropertyKey<Int>("DrawableResource")

fun SemanticsMatcher.Companion.hasDrawable(resourceId: Int): SemanticsMatcher {
    return expectValue(DrawableResourceKey, resourceId)
}

class MakeMarkKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun makeMark_isDisplayedInLightMode() {
        rule.setContent {
            MakeMark(
                lightMode = R.drawable.github_mark,
                darkMode = R.drawable.github_mark_white,
                contentDescription = "GitHub Logo",
                modifier = Modifier.testTag(MAKE_MARK_TAG).drawableResource(R.drawable.github_mark)
            )
        }

        rule.onNodeWithTag(MAKE_MARK_TAG)
            .assertIsDisplayed()
            .assert(SemanticsMatcher.hasDrawable(R.drawable.github_mark))
    }

    @Test
    fun makeMark_isDisplayedInDarkMode() {
        rule.setContent {
            MakeMark(
                lightMode = R.drawable.github_mark,
                darkMode = R.drawable.github_mark_white,
                contentDescription = "GitHub Logo",
                modifier = Modifier.testTag(MAKE_MARK_TAG).drawableResource(R.drawable.github_mark_white)
            )
        }

        rule.onNodeWithTag(MAKE_MARK_TAG)
            .assertIsDisplayed()
            .assert(SemanticsMatcher.hasDrawable(R.drawable.github_mark_white))
    }

    @Test
    fun makeMark_hasCorrectContentDescription() {
        rule.setContent {
            MakeMark(
                lightMode = R.drawable.github_mark,
                darkMode = R.drawable.github_mark_white,
                contentDescription = "GitHub Logo"
            )
        }

        rule.onNodeWithTag(MAKE_MARK_TAG)
            .assertContentDescriptionEquals("GitHub Logo")
    }
}