package com.example.chimp.application

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.chimp.BuildConfig
import com.example.chimp.infrastructure.UserInfoPreferencesRepository
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.screens.channels.model.ChannelsServices
import com.example.chimp.screens.createChannel.model.CreateChannelService
import com.example.chimp.screens.register.model.RegisterService
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.screens.register.model.FormValidation
import com.example.chimp.services.dummy.DummyFindChannelService
import com.example.chimp.services.http.CHIMPCreateChannelAPI
import com.example.chimp.services.http.ChIMPChannelsAPI
import com.example.chimp.services.http.ChIMPRegisterAPI
import com.example.chimp.services.validation.ChIMPFormValidator
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface DependenciesContainer {
    val createChannelService: CreateChannelService
    val loginService: RegisterService
    val channelsService: ChannelsServices
    val findChannelService: FindChannelService
    val formValidation: FormValidation
    val preferencesDataStore: DataStore<Preferences>
    val userInfoRepository: UserInfoRepository
}

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
        }
    }

    private val url: String by lazy { BuildConfig.API_URL }

    override val loginService: RegisterService by lazy {
        ChIMPRegisterAPI(client, url)
//        DummyRegisterService()
    }
    override val channelsService: ChannelsServices by lazy {
        ChIMPChannelsAPI(client, url, userInfoRepository.userInfo)
//        DummyChannelsService()
    }

    override val findChannelService: FindChannelService by lazy {
        DummyFindChannelService()
    }

    override val createChannelService: CreateChannelService by lazy {
        CHIMPCreateChannelAPI(client, url, userInfoRepository.userInfo)
    }

    override val formValidation: FormValidation by lazy {
        ChIMPFormValidator()
    }

    override val preferencesDataStore: DataStore<Preferences> by
    preferencesDataStore(name = "preferences")

    override val userInfoRepository: UserInfoRepository by lazy {
        UserInfoPreferencesRepository(preferencesDataStore)
    }
}