package com.example.shpp_task1.view


import com.bumptech.glide.Glide
import com.example.shpp_task1.R

//fun ContactsAdapter.loadAvatar(holder: ContactsAdapter.ContactsViewHolder, avatarUrl: String){
//    if (avatarUrl.isNotBlank()) {
//        Picasso.get()
//            .load(avatarUrl)
//            .fit()
//            .centerCrop()
//            .transform(CircleTransform())
//            .placeholder(R.drawable.ic_user_avatar)
//            .error(R.drawable.ic_user_avatar)
//            .into(holder.binding.photoImageView)
//    } else {
//        Picasso.get()
//            .load(avatarUrl)
//            .into(holder.binding.photoImageView)
//    }
//}


fun ContactsAdapter.loadAvatar(holder: ContactsAdapter.ContactsViewHolder, avatarUrl: String){
    if (avatarUrl.isNotBlank()) {
        Glide.with(holder.binding.photoImageView.context)
            .load(avatarUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_contact_avatar)
            .error(R.drawable.ic_contact_avatar)
            .into(holder.binding.photoImageView)
    } else {
        Glide.with(holder.binding.photoImageView.context)
            .load(R.drawable.ic_contact_avatar)
            .into(holder.binding.photoImageView)
    }
}
