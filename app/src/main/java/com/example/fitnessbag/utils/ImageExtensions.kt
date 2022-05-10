package com.example.fitnessbag.presentation.utils

import android.net.Uri
import android.widget.ImageView
import com.example.fitnessbag.R
import com.squareup.picasso.Picasso

fun ImageView.loadImage(imageUrl: String?) {
    if (imageUrl == null || imageUrl == "")
        return
    
    if(imageUrl.startsWith("android.resource")) {
        Picasso.get()
            .load(Uri.parse(imageUrl))
            .fit().centerCrop()
            .into(this)
        
        return
    }
    
    Picasso.get()
        .load(imageUrl)
        .fit().centerCrop()
        .into(this)
}

fun ImageView.loadImage(imageUrl: String?, placeholderDrawable: Int) {
    if (imageUrl == null || imageUrl == "") {
        Picasso.get()
            .load(placeholderDrawable)
            .fit().centerCrop()
            .into(this)
        
        return
    }

    Picasso.get()
        .load(Uri.parse(imageUrl))
        .fit().centerCrop()
        .placeholder(R.drawable.solid_grey)
        .into(this)
}