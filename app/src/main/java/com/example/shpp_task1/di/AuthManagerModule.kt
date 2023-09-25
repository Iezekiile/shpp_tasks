package com.example.shpp_task1.di

import com.example.shpp_task1.data.source.auth.AuthController
import com.example.shpp_task1.data.source.auth.AuthManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for auth manager
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AuthManagerModule {

    @Binds
    @Singleton
    abstract fun bindAuthManager(manager: AuthController): AuthManager
}