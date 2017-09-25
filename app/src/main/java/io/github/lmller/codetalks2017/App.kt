package io.github.lmller.codetalks2017

import android.app.Application

import io.github.lmller.codetalks2017.di.DaggerDiComponent
import io.github.lmller.codetalks2017.di.DiComponent
import io.github.lmller.codetalks2017.di.NetworkModule


class App : Application() {

    companion object {
        val injector: DiComponent by lazy {
            DaggerDiComponent.builder()
                    .networkModule(NetworkModule())
                    .build()
        }
    }
}
