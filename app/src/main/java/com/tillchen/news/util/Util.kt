package com.tillchen.news.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun String.getUserFriendlyDateFromISO(): String {
    val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    isoFormatter.timeZone = TimeZone.getTimeZone("UTC")
    val userFriendlyFormatter = SimpleDateFormat("MMMM dd yyyy, h:mm a", Locale.getDefault())
    return isoFormatter.parse(this)?.let { userFriendlyFormatter.format(it) } ?: ""
}
