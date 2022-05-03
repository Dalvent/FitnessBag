package com.example.fitnessbag.presentation

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.R
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxItemDecoration
import com.google.android.flexbox.FlexboxLayoutManager


fun RecyclerView.applyTagsStyle(context: Context) {
    val flexboxLayoutManager = FlexboxLayoutManager(context)
    flexboxLayoutManager.flexDirection = FlexDirection.ROW
    this.layoutManager = flexboxLayoutManager

    val itemDecoration = FlexboxItemDecoration(context)
    itemDecoration.setOrientation(FlexboxItemDecoration.BOTH)
    itemDecoration.setDrawable(
        ContextCompat.getDrawable(
            context,
            R.drawable.shape_transparent_w8_h4
        )
    )
    this.addItemDecoration(itemDecoration)
}