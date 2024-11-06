package com.example.chimp.model.errors

/**
 * ResponseErrors is a data class that represents an error response from the server.
 *
 * @property cause the cause of the error
 * @property urlInfo the URL that explains the error
 */
data class ResponseErrors(
    val cause: String,
    val urlInfo: String,
)
