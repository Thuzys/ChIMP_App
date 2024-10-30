package com.example.chimp.model.users

data class UserName(
    val name: String
) {
    init {
        require(name.isNotBlank()) { "Name must not be blank." }
    }
}
