package com.example.chimp.viewModel.state

import com.example.chimp.model.about.About

sealed interface ChimpState {

    class AboutDevState(
        aboutList: List<About>,
        aboutSelectorsList: List<AboutSelector>? = null
    ) : ChimpState {
        val aboutSelectors: List<AboutSelector> =
            aboutSelectorsList ?: aboutList.map { AboutSelector(it) }
    }
}

data class AboutSelector(
    val about: About,
    val isExpanded: Boolean = false,
    val showDialog: Boolean = false,
) { }
