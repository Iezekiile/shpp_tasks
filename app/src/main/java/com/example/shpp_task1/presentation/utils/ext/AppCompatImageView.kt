package com.example.shpp_task1.presentation.utils.ext


import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.shpp_task1.R
import com.squareup.picasso.Picasso
/*
 * Extension functions for loading images.
 */


//todo ext for :
// if (FeatureFlags.USE_GLIDE) avatar.setImageByGlide(avatarUri.toString())
//  else avatar.setImageByPicasso(avatarUri.toString())


/**
 * Load image by Picasso.
 * @param imageUrl - url of image.
 */
fun AppCompatImageView.setImageByPicasso(imageUrl: String?) {
    if (imageUrl != null) {
        Picasso.get()
            .load(imageUrl)
            .fit()
            .centerCrop()
            .transform(CircleTransform())
            .placeholder(R.drawable.ic_contact_avatar)
            .error(R.drawable.ic_contact_avatar)
            .into(this)
    } else {
        Picasso.get()
            .load(R.drawable.ic_contact_avatar)
            .into(this)
    }
}


/**
 * Load image by Glide.
 * @param imageUrl - url of image.
 */
fun AppCompatImageView.setImageByGlide(imageUrl: String?) {
    Glide.with(context)
        .load(imageUrl)
        .apply(GLIDE_OPTIONS)
        .into(this)
}

val GLIDE_OPTIONS = RequestOptions()
    .centerCrop()
    .circleCrop()
    .placeholder(R.drawable.ic_contact_avatar)
    .error(R.drawable.ic_contact_avatar)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .priority(Priority.HIGH)

