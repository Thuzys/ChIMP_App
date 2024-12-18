package com.example.chimp.utils

import androidx.test.runner.AndroidJUnitRunner
import com.example.chimp.application.ChIMPApplicationReplacer

@Suppress("unused")
class ChIMPApplicationTestRunner: AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: android.content.Context?
    ): android.app.Application {
        return super.newApplication(cl, ChIMPApplicationReplacer::class.java.name, context)
    }
}