package com.example.fitnessbag.domain.services

import android.net.Uri
import com.example.fitnessbag.MainActivity
import com.github.dhaval2404.imagepicker.ImagePicker

class DhavalImagePickerService : ImagePickerService {
    private var onResult: ((Uri) -> Unit)? = null

    override fun pickImage(onResult: (Uri) -> Unit) {
        this.onResult = onResult
        ImagePicker.with(MainActivity.activity.get()!!).start()
    }

    override fun invokeResult(uri: Uri) {
        onResult?.invoke(uri)
        onResult = null
    }
}