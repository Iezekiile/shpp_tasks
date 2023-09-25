package com.example.shpp_task1.di

import com.example.shpp_task1.data.source.contacts.ContactsManager
import com.example.shpp_task1.data.source.contacts.RepositoryContacts
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for providing dependencies for the [ContactsManager] class.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class ContactManagerModule {

    @Binds
    @Singleton
    abstract fun bindContactManager(manager: RepositoryContacts): ContactsManager
}