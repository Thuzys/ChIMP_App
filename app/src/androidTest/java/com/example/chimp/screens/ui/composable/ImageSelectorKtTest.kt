package com.example.chimp.screens.ui.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import org.junit.Rule
import org.junit.Test
import com.example.chimp.R

class ImageSelectorKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun imageSelector_displaysSelectedImage() {
        rule.setContent {
            ImageSelector(image = R.drawable.default_icon)
        }

        rule.onNodeWithTag(IMAGE_SELECTOR_SELECTED_IMAGE)
            .assertIsDisplayed()
    }

    @Test
    fun imageSelector_opensImageList_whenClicked() {
        rule.setContent {
            ImageSelector(image = R.drawable.default_icon)
        }

        rule.onNodeWithTag(IMAGE_SELECTOR_SELECTED_IMAGE)
            .performClick()

        rule.onNodeWithTag(IMAGE_SELECTOR_LIST)
            .assertIsDisplayed()
    }

    @Test
    fun imageSelector_selectsImageFromList() {
        var selectedImage = R.drawable.default_icon
        rule.setContent {
            ImageSelector(image = selectedImage, onImageSelected = { selectedImage = it })
        }

        rule.onNodeWithTag(IMAGE_SELECTOR_SELECTED_IMAGE)
            .performClick()

        rule.onNodeWithTag("image_${R.drawable.icon1}")
            .performClick()

        assert(selectedImage == R.drawable.icon1)
    }

    @Test
    fun imageSelector_closesImageList_afterSelection() {
        rule.setContent {
            ImageSelector(image = R.drawable.default_icon)
        }

        rule.onNodeWithTag(IMAGE_SELECTOR_SELECTED_IMAGE)
            .performClick()

        rule.onNodeWithTag("image_${R.drawable.icon1}")
            .performClick()

        rule.onNodeWithTag(IMAGE_SELECTOR_LIST)
            .assertDoesNotExist()
    }
}