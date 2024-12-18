package com.example.chimp.services.http.dtos.input.error

import com.example.chimp.models.errors.ResponseError
import kotlinx.serialization.Serializable

@Serializable
internal data class ErrorInputModel(
    val type: String,
    val title: String,
) {
    fun toResponseError() = ResponseError(cause = title, urlInfo = type)
}
