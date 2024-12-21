package com.example.chimp.screens.channel.model.time

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

internal fun Timestamp.formatTime(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return sdf.format(this)
}