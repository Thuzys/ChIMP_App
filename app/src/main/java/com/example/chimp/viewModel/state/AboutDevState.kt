package com.example.chimp.viewModel.state

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.chimp.model.dev.Dev

//TODO: Add Saver annotation to AboutDevState
data class AboutDevState(
    private val devList: List<Dev>,
    private val context: Context,
    val devSelectorsList: Map<Dev, AboutSelector> = devList.associateWith { AboutSelector() }
) {
    data class AboutSelector(
        val isExpanded: Boolean = false,
        val showDialog: Boolean = false,
    )

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

    fun toggleExpanded(index: Dev): AboutDevState {
        val newAboutSelectorsList = devSelectorsList.mapValues { (dev, selector) ->
            if (dev == index) {
                selector.copy(isExpanded = !selector.isExpanded)
            } else {
                selector.copy(isExpanded = false)
            }
        }
        return copy(devSelectorsList = newAboutSelectorsList)
    }

    fun toggleDialog(index: Dev): AboutDevState {
        val newAboutSelectorsList = devSelectorsList.mapValues { (dev, selector) ->
            if (dev == index) {
                selector.copy(showDialog = !selector.showDialog)
            } else {
                selector.copy(showDialog = false)
            }
        }
        return copy(devSelectorsList = newAboutSelectorsList)
    }
}
