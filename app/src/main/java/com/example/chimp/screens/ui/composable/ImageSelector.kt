package com.example.chimp.screens.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R

private const val IMAGE_SIZE = 240

private val listOfImages = listOf(
    R.drawable.default_icon,
    R.drawable.icon1,
    R.drawable.icon2,
    R.drawable.icon3,
    R.drawable.icon4,
    R.drawable.icon5,
    R.drawable.icon6,
    R.drawable.icon7,
    R.drawable.icon8,
    R.drawable.icon9,
    R.drawable.icon10,
    R.drawable.icon11,
    R.drawable.icon12,
    R.drawable.icon13,
    R.drawable.icon14,
    R.drawable.icon15,
    R.drawable.icon16,
    R.drawable.icon17,
    R.drawable.icon18,
    R.drawable.icon19,
    R.drawable.icon20,
)

@Composable
fun ImageSelector(
    modifier: Modifier = Modifier,
    image: Int,
    onImageSelected: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier,
    ){
        var isSelecting by remember { mutableStateOf(false) }
        if (!isSelecting) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "selected image",
                modifier = Modifier
                    .padding(8.dp)
                    .clip(shape = MaterialTheme.shapes.extraLarge)
                    .height(IMAGE_SIZE.dp)
                    .fillMaxWidth()
                    .clickable { isSelecting = true },
                contentScale = ContentScale.Crop,
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((IMAGE_SIZE*2).dp)
            ) {
                items(listOfImages) { image ->
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = "image $image",
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(shape = MaterialTheme.shapes.extraLarge)
                            .height(IMAGE_SIZE.dp)
                            .fillMaxWidth()
                            .clickable {
                                onImageSelected(image)
                                isSelecting = false
                            },
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ImageSelectorPreview() {
    var selectedImage by remember { mutableIntStateOf(R.drawable.default_icon) }
    ImageSelector(
        image = selectedImage
    ) {
        selectedImage = it
    }
}