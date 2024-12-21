package com.example.chimp.models.channel

enum class Visibility {
    PUBLIC {
        override fun toString() = "PUBLIC"
    },
    PRIVATE {
        override fun toString() = "PRIVATE"
    }
}