package com.example.fitnessbag.utils

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImageUrl(imageUrl: String) {
    Picasso.get().load(imageUrl).into(this);
}