package com.example.shpp_task1.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shpp_task1.R
import com.example.shpp_task1.databinding.ItemUserBinding
import com.example.shpp_task1.model.Contact

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    var contacts: List<Contact> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = contacts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return ContactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = contacts[position]
        with(holder.binding) {
            contactNameTextView.text = contact.name
            contactCarrierTextView.text = contact.carrier
            loadAvatar(holder, contact.avatar)
        }
    }

    inner class ContactsViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setBackgroundResource(R.drawable.bg_view_holder)
        }
    }

}