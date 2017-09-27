package io.github.lmller.codetalks2017.di

import dagger.Component
import io.github.lmller.codetalks2017.MainActivity


@Component(modules = arrayOf(NetworkModule::class))
interface DiComponent {
    fun injectInto(mainActivity: MainActivity)
}