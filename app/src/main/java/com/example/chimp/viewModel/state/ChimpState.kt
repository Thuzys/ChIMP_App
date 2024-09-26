package com.example.chimp.viewModel.state

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.chimp.model.about.About
//TODO: Add Parcelable annotation to the model
sealed interface ChimpState {

    data class AboutDevState(
        private val aboutList: List<About>,
        private val context: Context,
        val aboutSelectorsList: Map<About, AboutSelector> = aboutList.associateWith { AboutSelector() }
    ) : ChimpState {
        fun linkMaker(uri: Uri) {
            val intent = Intent(Intent.ACTION_VIEW)
                .apply {
                    data = uri
                }
            context.startActivity(intent)
        }
        fun sendMaker(address: String) {
            val intent = Intent(Intent.ACTION_SEND)
                .apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
                }
            context.startActivity(intent)
        }
        fun toggleExpanded(index: About): AboutDevState {
            val newAboutSelectorsList = aboutSelectorsList.mapValues { (dev, selector) ->
                if (dev == index) {
                    selector.copy(isExpanded = !selector.isExpanded)
                } else {
                    selector.copy(isExpanded = false)
                }
            }
            return copy(aboutSelectorsList = newAboutSelectorsList)
        }
        fun toggleDialog(index: About): AboutDevState {
            val newAboutSelectorsList = aboutSelectorsList.mapValues { (dev, selector) ->
                if (dev == index) {
                    selector.copy(showDialog = !selector.showDialog)
                } else {
                    selector.copy(showDialog = false)
                }
            }
            return copy(aboutSelectorsList = newAboutSelectorsList)
        }
    }
}

data class AboutSelector(
    val isExpanded: Boolean = false,
    val showDialog: Boolean = false,
)
