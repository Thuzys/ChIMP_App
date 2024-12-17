package com.example.chimp.application

import android.app.Application
import com.example.chimp.BuildConfig
import com.example.chimp.screens.register.model.RegisterService
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.screens.register.model.FormValidation
import com.example.chimp.services.dummy.DummyFindChannelService
import com.example.chimp.services.dummy.DummyRegisterService
import com.example.chimp.services.http.ChIMPRegisterAPI
import com.example.chimp.services.validation.ChIMPFormValidator
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface DependenciesContainer {
    val loginService: RegisterService
    val findChannelService: FindChannelService
    val formValidation: FormValidation
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
        //ChIMPRegisterAPI(client, url)
        DummyRegisterService()
    }

    override val findChannelService: FindChannelService by lazy {
        DummyFindChannelService()
    }

    override val formValidation: FormValidation by lazy {
        ChIMPFormValidator()
    }
}