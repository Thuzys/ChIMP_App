package com.example.chimp.models.channel

enum class AccessControl {
    READ_ONLY {
        override fun toString() = "READ_ONLY"
    },
    READ_WRITE {
        override fun toString() = "READ_WRITE"
    }
}