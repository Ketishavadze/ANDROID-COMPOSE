package com.example.compose.ui.snackbar

/**
 * UI-level messages that can be shown to the user.
 * Keep it simple; add fields if you later want action labels, etc.
 */
sealed interface UiMessage {
    data class Error(val text: String) : UiMessage
    data class Info(val text: String) : UiMessage
}
