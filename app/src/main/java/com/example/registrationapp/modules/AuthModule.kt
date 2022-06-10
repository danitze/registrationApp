package com.example.registrationapp.modules

import com.example.registrationapp.storage.remote.retrofit.adapter.ApiResultCallAdapterFactory
import com.example.registrationapp.storage.remote.retrofit.services.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideBaseUrl(): String = "https://tips-api-staging.crio-server.com"

    @Singleton
    @Provides
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(ApiResultCallAdapterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

}