package com.example.chimp.login.model

sealed interface LoginErrors {
    data class InvalidCredentials(val message: String) : LoginErrors
}