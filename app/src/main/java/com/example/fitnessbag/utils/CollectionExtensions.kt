package com.example.fitnessbag.utils


fun <T> MutableIterable<T>.removeWithAfter(predicate: (T) -> Boolean, afterRemove: (T, Int) -> Unit) {
    val iterator = this.iterator()
    var i = 0
    
    while (iterator.hasNext()) {
        val next = iterator.next()
        if (predicate.invoke(next)) {
            iterator.remove()
            afterRemove.invoke(next, i)
        }
        i++
    }
}