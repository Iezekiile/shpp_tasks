package com.example.shpp_task1.di

import com.example.shpp_task1.data.model.Contact
import com.example.shpp_task1.multiselect.ContactsMultiselectHandler
import com.example.shpp_task1.multiselect.MultiselectHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Qualifier

/**
 * Module for contacts multiselect
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ContactsMultiselect

@Module
@InstallIn(ViewModelComponent::class)
class ContactsMultiselectModule {

    @Provides
    @ContactsMultiselect
    fun provideMultiselectHandler(): MultiselectHandler<Contact> {
        return ContactsMultiselectHandler()
    }

}
