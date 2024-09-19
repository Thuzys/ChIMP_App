package com.example.chimp.view.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AboutDevScreen(modifier: Modifier = Modifier) {
    Column(
        modifier =
        modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        var isExpanded by remember { mutableStateOf(false) }
        /*TODO: pass the others devs (maybe a list)*/
        AboutDeveloper(isExpanded = isExpanded) {
            isExpanded = !isExpanded
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutDevScreen()
}