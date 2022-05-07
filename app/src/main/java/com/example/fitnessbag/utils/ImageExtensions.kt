package com.example.fitnessbag.presentation.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImage(imageUrl: String?) {
    if (imageUrl == null || imageUrl == "")
        return
    
    Picasso.get()
        .load(imageUrl)
        .into(this)
}