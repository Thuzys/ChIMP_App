package com.example.chimp.services.http.utlis

import com.example.chimp.models.users.User
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal fun HttpRequestBuilder.makeHeader(curr: User) {
    header("Authorization", "Bearer ${curr.token.token}")
    makeHeader()
}

internal fun HttpRequestBuilder.makeHeader() {
    contentType(ContentType.Application.Json)
    contentType(ContentType.Application.ProblemJson)
}