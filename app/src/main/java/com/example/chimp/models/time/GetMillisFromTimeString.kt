package com.example.chimp.models.time

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

fun getMillisFromTimeString(timeString: String): Long {
    return when(timeString) {
        "30 minutes" -> 30 * 60 * 1000L
        "1 hour" -> 60 * 60 * 1000L
        "1 day" -> 24 * 60 * 60 * 1000L
        "1 week" -> 7 * 24 * 60 * 60 * 1000L
        else -> 30 * 60 * 1000L
    }
}

fun Timestamp.toOptionFormat(): String {
    val minutes = time / 60 / 1000
    val hours = time / 60 / 60 / 1000
    val days = time / 24 / 60 / 60 / 1000
    val weeks = time / 7 / 24 / 60 / 60 / 1000

    return when {
        time < 60 * 60 * 1000 -> "$minutes minute${if (minutes == 1L) "" else "s"}"
        time < 24 * 60 * 60 * 1000 -> "$hours hour${if (hours == 1L) "" else "s"}"
        time < 7 * 24 * 60 * 60 * 1000 -> "$days day${if (days == 1L) "" else "s"}"
        else -> "$weeks week${if (weeks == 1L) "" else "s"}"
    }
}

/**
 * Formats the timestamp to a string with the format "yyyy-MM-dd'T'HH:mm:ss"
 *
 * @param timestamp the timestamp to format
 */
fun formatTimestamp(timestamp: Timestamp): String =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(timestamp)