package com.example.shpp_task1.model

import com.github.javafaker.Faker

typealias ContactsListener = (users: List<Contact>) -> Unit

/*
* A class that represents the data source for the list of contacts.
*/
class ContactsData {
    private var contacts = mutableListOf<Contact>()
    private val listeners = mutableSetOf<ContactsListener>()

    /**
     * Constructor of the class that generates random data for the list of contacts.
     */
    init {
        val faker = Faker.instance()
        contacts = (1..20).map {
            Contact(
                id = it.toLong(),
                username = faker.name().name(),
                career = faker.company().profession(),
                avatar = IMAGES[it % IMAGES.size],
                email = faker.internet().emailAddress(),
                phone = faker.phoneNumber().phoneNumber(),
                address = faker.address().fullAddress(),
                birthday = faker.date().birthday().toString()
            )
        }.toMutableList()
    }

    /**
     * Method to delete a contact from the list.
     *
     * @param contact The contact to be deleted.
     * @return The index of the deleted contact, or -1 if the contact was not found.
     */
    fun deleteContact(contact: Contact): Int {
        val indexToDelete = contacts.indexOfFirst { it.id == contact.id }
        if (indexToDelete != -1) {
            contacts.removeAt(indexToDelete)
            notifyChanges()
        }
        return indexToDelete
    }

    /**
     * Method to get a contact at a specific index.
     *
     * @param index The index of the contact in the list.
     * @return The contact at the specified index.
     */
    fun getContactOnIndex(index: Int): Contact {
        return contacts[index]
    }

    /**
     * Method to get the entire list of contacts.
     *
     * @return The list of contacts.
     */
    fun getContacts(): MutableList<Contact> {
        return contacts
    }

    /**
     * Method to add a new contact to the list.
     *
     * @param contact The new contact to add.
     */
    fun addContact(contact: Contact) {
        contacts.add(0, contact)
        notifyChanges()
    }

    /**
     * Private method to notify listeners about changes in the data.
     */
    private fun notifyChanges() {
        listeners.forEach { it.invoke(contacts) }
    }

    companion object {
        private val IMAGES = mutableListOf(
            "https://images.unsplash.com/photo-1600267185393-e158a98703de?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NjQ0&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1579710039144-85d6bdffddc9?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0Njk1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1488426862026-3ee34a7d66df?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODE0&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1620252655460-080dbec533ca?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzQ1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1613679074971-91fc27180061?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzUz&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1485795959911-ea5ebf41b6ae?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzU4&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1545996124-0501ebae84d0?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzY1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/flagged/photo-1568225061049-70fb3006b5be?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0Nzcy&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1567186937675-a5131c8a89ea?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODYx&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1546456073-92b9f0a8d413?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODY1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800"
        )
    }
}