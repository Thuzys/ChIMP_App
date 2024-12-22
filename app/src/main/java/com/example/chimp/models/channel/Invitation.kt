package com.example.chimp.models.channel

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Represents an invitation
 */
interface Invitation {

    /**
     * Formats the timestamp to a string with the format "yyyy-MM-dd'T'HH:mm:ss"
     *
     * @param timestamp the timestamp to format
     */
    fun formatTimestamp(timestamp: Timestamp): String =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(timestamp)
}