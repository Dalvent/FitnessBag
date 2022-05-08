package com.example.fitnessbag.domain

import android.net.Uri

interface ImagePickerService {
    fun pickImage(onResult: (Uri) -> Unit)
    fun invokeResult(uri: Uri)
}