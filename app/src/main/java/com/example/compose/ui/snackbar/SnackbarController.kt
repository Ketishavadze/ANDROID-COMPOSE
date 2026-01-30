package com.example.compose.ui.snackbar


import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Single responsibility: send snackbar messages from anywhere (VM/Screen)
 * and collect them inside Scaffold once.
 */
class SnackbarController {
    private val _messages = Channel<UiMessage>(capacity = Channel.BUFFERED)
    val messages: Flow<UiMessage> = _messages.receiveAsFlow()

    suspend fun send(message: UiMessage) {
        _messages.send(message)
    }
}
