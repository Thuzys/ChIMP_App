package com.example.chimp.screens.ui.composable

import androidx.compose.animation.core.LinearEasing
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.unit.LayoutDirection
import org.junit.Rule
import org.junit.Test

class MarqueeKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun marquee_scrolls_content_when_too_wide() {
        rule.setContent {
            Marquee(
                modifier = Modifier.testTag("Marquee"),
                params = MarqueeParams(
                    period = 1000,
                    direction = LayoutDirection.Ltr,
                    easing = LinearEasing,
                    gradientEnabled = false,
                    gradientEdgeColor = Color.Transparent
                )
            ) {
                Text(
                    text = "This is a very long text that should be scrolled",
                    color = Color.Black
                )
            }
        }

        rule.onAllNodesWithText("This is a very long text that should be scrolled", useUnmergedTree = true)
            .filter(hasParent(hasTestTag("Marquee")))
            .onFirst()
            .assertIsDisplayed()
    }
}