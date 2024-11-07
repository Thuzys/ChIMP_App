package com.example.chimp.application

import android.app.Application
import com.example.chimp.screens.login.model.LoginService
import com.example.chimp.services.dummy.DummyLoginService

interface DependenciesContainer {
    val loginService: LoginService
}

class ChIMPApplication : Application(), DependenciesContainer {
    override val loginService: LoginService by lazy {
        DummyLoginService()
    }

}