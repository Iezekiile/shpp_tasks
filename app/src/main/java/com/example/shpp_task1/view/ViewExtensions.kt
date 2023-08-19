package com.example.shpp_task1.view


import com.bumptech.glide.Glide
import com.example.shpp_task1.R
import com.example.shpp_task1.view.adapters.ContactsAdapter

//  Image loading extension function by using Picasso

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


/**
 * Image loading extension function by using Glide
 * @param holder - view holder from adapter
 * @param avatarUrl - image url
 */
fun ContactsAdapter.loadAvatar(holder: ContactsAdapter.ContactsViewHolder, avatarUrl: String) {
    if (avatarUrl.isNotBlank()) {
        Glide.with(holder.binding.avatar.context)
            .load(avatarUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_contact_avatar)
            .error(R.drawable.ic_contact_avatar)
            .into(holder.binding.avatar)
    } else {
        Glide.with(holder.binding.avatar.context)
            .load(R.drawable.ic_contact_avatar)
            .into(holder.binding.avatar)
    }
}
