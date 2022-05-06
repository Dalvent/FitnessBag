package com.example.fitnessbag.utils

import java.lang.Exception

class ValidationException(val reason: String = "") : Exception() {
}