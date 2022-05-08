package com.example.fitnessbag.domain

import java.util.*

fun String.startWithLower(arg: String) =
    this.lowercase(Locale.getDefault())
        .startsWith(arg.lowercase(Locale.getDefault()))
