package com.example.chimp.observeConnectivity

import android.content.Context
import android.net.ConnectivityManager
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status.DISCONNECTED
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NetworkConnectivityObserver(
    context: Context
): ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnectivityObserver.Status> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: android.net.Network) {
                    super.onAvailable(network)
                    launch { send(ConnectivityObserver.Status.CONNECTED) }
                }

                override fun onLost(network: android.net.Network) {
                    super.onLost(network)
                    launch { send(DISCONNECTED) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(DISCONNECTED) }
                }

                override fun onLosing(network: android.net.Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(DISCONNECTED) }
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}