package com.example.compose.ui.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toRelativeTimeString(now: Long = System.currentTimeMillis()): String {
    val diff = now - this

    return when {
        diff < 60_000L -> "Just now"
        diff < 3_600_000L -> "${diff / 60_000L}m ago"
        diff < 86_400_000L -> "${diff / 3_600_000L}h ago"
        diff < 604_800_000L -> "${diff / 86_400_000L}d ago"
        else -> SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(this))
    }
}
