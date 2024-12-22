package com.example.chimp.models.errors

/**
 * ResponseErrors is a data class that represents an error response from the server.
 *
 * @property cause the cause of the error
 * @property urlInfo the URL that explains the error
 */
data class ResponseError(
    val cause: String,
    val urlInfo: String = "",
) {
    companion object {
        val Unauthorized = ResponseError("Unauthorized")
        val NotFound = ResponseError("Not Found")
        val BadRequest = ResponseError("Bad Request")
        val InternalServerError = ResponseError("Internal Server Error")
        val NoInternet = ResponseError("No internet connection")
        val NoChannelContext = ResponseError("No channel context")
        val NoUserContext = ResponseError("No user context, please login again")
        val Unknown =
            ResponseError("Unknown error, please verify internet connection and try again later.")
    }
}
