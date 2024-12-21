package com.example.chimp.observeConnectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status {
        CONNECTED,
        DISCONNECTED
    }
}