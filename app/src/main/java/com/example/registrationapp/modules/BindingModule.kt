package com.example.registrationapp.modules

import com.example.registrationapp.repo.abstraction.AuthRepo
import com.example.registrationapp.repo.implementation.AuthRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingModule {
    @Binds
    abstract fun bindAuthRepo(authRepoImpl: AuthRepoImpl): AuthRepo
}