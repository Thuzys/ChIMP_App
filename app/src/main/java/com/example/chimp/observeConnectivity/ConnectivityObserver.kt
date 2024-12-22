package com.example.chimp.observeConnectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    val connectivity: Flow<Status>

    enum class Status {
        CONNECTED,
        DISCONNECTED
    }
}