package io.github.lmller.codetalks2017.di

import dagger.Module
import dagger.Provides
import io.github.lmller.codetalks2017.OpenThesaurus
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

        return Retrofit.Builder()
                .baseUrl("https://www.openthesaurus.de")
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    fun provideThesaurus(retrofit: Retrofit): OpenThesaurus {
        return OpenThesaurus(retrofit.create(OpenThesaurus.Api::class.java))
    }
}