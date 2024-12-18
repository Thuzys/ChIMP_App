package com.example.chimp.application

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.chimp.infrastructure.UserInfoPreferencesRepository
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.screens.channels.model.channel.ChannelsServices
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.screens.register.model.FormValidation
import com.example.chimp.screens.register.model.RegisterService

class ChIMPApplicationReplacer: Application(), DependenciesContainer {
    override val loginService: RegisterService
        get() = TODO("Not yet implemented")
    override val channelsService: ChannelsServices
        get() = TODO("Not yet implemented")
    override val findChannelService: FindChannelService
        get() = TODO("Not yet implemented")
    override val formValidation: FormValidation
        get() = TODO("Not yet implemented")
    override val preferencesDataStore: DataStore<Preferences> by
        preferencesDataStore(name = "test_prefs")
    override val userInfoRepository: UserInfoRepository by lazy {
        UserInfoPreferencesRepository(preferencesDataStore)
    }
}