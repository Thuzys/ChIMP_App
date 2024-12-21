package com.example.chimp.application

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.chimp.BuildConfig
import com.example.chimp.infrastructure.ChannelPreferencesRepository
import com.example.chimp.infrastructure.UserInfoPreferencesRepository
import com.example.chimp.models.repository.ChannelRepository
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.observeConnectivity.ConnectivityObserver
import com.example.chimp.observeConnectivity.NetworkConnectivityObserver
import com.example.chimp.screens.channel.model.ChannelService
import com.example.chimp.screens.channels.model.ChannelsServices
import com.example.chimp.screens.register.model.RegisterService
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.screens.register.model.FormValidation
import com.example.chimp.services.http.ChIMPChannelAPI
import com.example.chimp.services.http.CHIMPFindChannelAPI
import com.example.chimp.services.http.ChIMPChannelsAPI
import com.example.chimp.services.http.ChIMPRegisterAPI
import com.example.chimp.services.validation.ChIMPFormValidator
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.sse.SSE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.minutes

interface DependenciesContainer {
    val loginService: RegisterService
    val channelsService: ChannelsServices
    val channelService: ChannelService
    val findChannelService: FindChannelService
    val formValidation: FormValidation
    val userInfoRepository: UserInfoRepository
    val channelRepository: ChannelRepository
    val preferencesDataStore: DataStore<Preferences>
    val connectivityObserver: ConnectivityObserver
}

private const val RECONNECTION_TIME_IN_MINUTES = 5

private const val REQUEST_TIMEOUT_IN_MILLIS = 1500000L

private const val CONNECT_TIMEOUT_IN_MILLIS = 1500000L

private const val SOCKET_TIMEOUT_IN_MILLIS = 1500000L

class ChIMPApplication : Application(), DependenciesContainer {
    private val client by lazy {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(HttpCookies)
            install(SSE) {
                reconnectionTime = RECONNECTION_TIME_IN_MINUTES.minutes
                showRetryEvents()
                showCommentEvents()
            }
            install(HttpTimeout) {
                requestTimeoutMillis = REQUEST_TIMEOUT_IN_MILLIS
                connectTimeoutMillis = CONNECT_TIMEOUT_IN_MILLIS
                socketTimeoutMillis = SOCKET_TIMEOUT_IN_MILLIS
            }
        }
    }

    private val url: String by lazy { BuildConfig.API_URL }

    override val loginService: RegisterService by lazy {
        ChIMPRegisterAPI(client, url)
    }

    override val channelsService: ChannelsServices by lazy {
        ChIMPChannelsAPI(
            client,
            url,
            userInfoRepository.userInfo,
            connectivityObserver.connectivity
        )
    }

    override val findChannelService: FindChannelService by lazy {
        CHIMPFindChannelAPI(client, url, userInfoRepository.userInfo, connectivityObserver.connectivity)
    }

    override val formValidation: FormValidation by lazy {
        ChIMPFormValidator()
    }

    override val channelService: ChannelService by lazy {
        ChIMPChannelAPI(
            client,
            url,
            userInfoRepository.userInfo,
            channelRepository.channelInfo,
            connectivityObserver.connectivity
        )
    }

    override val preferencesDataStore: DataStore<Preferences> by
    preferencesDataStore(name = "preferences")

    override val connectivityObserver: ConnectivityObserver by lazy {
        NetworkConnectivityObserver(applicationContext)
    }

    override val userInfoRepository: UserInfoRepository by lazy {
        UserInfoPreferencesRepository(preferencesDataStore)
    }

    override val channelRepository: ChannelRepository by lazy {
        ChannelPreferencesRepository(preferencesDataStore)
    }
}