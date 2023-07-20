package com.example.shpp_task1.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shpp_task1.R
import com.example.shpp_task1.databinding.ActivityMycontactsBinding
import com.example.shpp_task1.databinding.ItemContactBinding
import com.example.shpp_task1.viewmodel.ContactsViewModel

class MyContacts : AppCompatActivity() {

    private lateinit var binding: ActivityMycontactsBinding
    private lateinit var itemContactbinding: ItemContactBinding
    private lateinit var adapter: ContactsAdapter
    private lateinit var contactsViewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMycontactsBinding.inflate(layoutInflater)
        adapter = ContactsAdapter()

        contactsViewModel = ViewModelProvider(this)[ContactsViewModel::class.java]
        contactsViewModel.contacts.observe(this, Observer {
            adapter.contacts = it
        })
        binding.recyclerContacts.layoutManager = LinearLayoutManager(this)
        val spaceBetweenItemsInPixels = resources.getDimensionPixelSize(R.dimen.margin_all_l)
        binding.recyclerContacts.addItemDecoration(SpaceItemDecoration(spaceBetweenItemsInPixels))
        binding.recyclerContacts.adapter = adapter

        setContentView(binding.root)
    }
}