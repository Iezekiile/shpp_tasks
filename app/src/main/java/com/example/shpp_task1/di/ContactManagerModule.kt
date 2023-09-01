package com.example.shpp_task1.di

import com.example.shpp_task1.data.source.ContactManager
import com.example.shpp_task1.data.source.RepositoryContacts
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module for providing dependencies for the [ContactManager] class.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class ContactManagerModule {

        @Binds
        abstract fun bindContactManager(manager: RepositoryContacts): ContactManager
}