package com.example.chimp.screens.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Taf for [MyTextField] used for testing.
 */
const val MY_TEXT_FIELD_TAG = "MyTextField"

/**
 * The height of the spacer between the text label and the text field.
 */
private const val TEXT_FIELD_SPACER_HEIGHT = 10

/**
 * The radius of the rounded corners of the text field.
 */
private const val ROUNDED_CORNER_RADIUS = 10

/**
 * The default height of the text field.
 */
private const val DEFAULT_TEXT_FIELD_HEIGHT = 42

/**
 * The background color of the text field.
 */
private val background = Color(0xFFEFEEEE)

private val trailingIconTint = Color(0xFF828282)

/**
 * The horizontal padding of the text field.
 */
private const val HORIZONTAL_PADDING = 10

/**
 * The weight of the box.
 */
private const val BOX_WEIGHT = 1f

/**
 * A text field with a label.
 *
 * @param modifier The modifier to apply to this layout node.
 * @param label The text to display as the label.
 * @param value The text to display in the text field.
 * @param onValueChange The callback to be called when the value changes.
 * @param height The height of the text field.
 * @param keyBoardOptions The keyboard options to apply to the text field.
 * @param keyBoardAction The keyboard actions to apply to the text field.
 * @param visualTransformation The visual transformation to apply to the text field.
 * @param trailingIcon The icon to display at the end of the text field.
 */
@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    height: Int = DEFAULT_TEXT_FIELD_HEIGHT,
    keyBoardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyBoardAction: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: ImageVector? = null,
    onTrailingIconChange: () -> Unit = {}
) {
    Column(
        modifier = modifier.testTag(MY_TEXT_FIELD_TAG)
    ) {
        Text(text = label)
        Spacer(modifier = Modifier.height(TEXT_FIELD_SPACER_HEIGHT.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = keyBoardOptions,
            keyboardActions = keyBoardAction,
            visualTransformation = visualTransformation,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = HORIZONTAL_PADDING.dp)
                    .clip(RoundedCornerShape(ROUNDED_CORNER_RADIUS.dp))
                    .height(height.dp)
                    .background(color = background),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(BOX_WEIGHT)
                        .padding(horizontal = HORIZONTAL_PADDING.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    it.invoke()
                }
                trailingIcon?.let { icon ->
                    IconButton(
                        onClick = onTrailingIconChange
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = "Trailing Icon",
                            tint = trailingIconTint
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyTextFieldPreview() {
    MyTextField(
        label = "Hello, World!"
    )
}