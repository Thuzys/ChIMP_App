package com.example.chimp.application

import android.app.Application
import com.example.chimp.findChannel.model.FindChannelService
import com.example.chimp.login.model.LoginService
import com.example.chimp.services.dummy.DummyFindChannelService
import com.example.chimp.services.dummy.DummyLoginService

interface DependenciesContainer {
    val loginService: LoginService
    val findChannelService: FindChannelService
}

class ChIMPApplication : Application(), DependenciesContainer {
    override val loginService: LoginService by lazy {
        DummyLoginService()
    }

    override val findChannelService: FindChannelService by lazy {
        DummyFindChannelService()
    }
}