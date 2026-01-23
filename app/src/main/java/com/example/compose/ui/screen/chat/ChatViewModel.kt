package com.example.compose.ui.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.domain.usecase.GetChatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChats: GetChatsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ChatContract.State())
    val state: StateFlow<ChatContract.State> = _state.asStateFlow()

    private val _effects = Channel<ChatContract.Effect>(Channel.BUFFERED)
    val effects: Flow<ChatContract.Effect> = _effects.receiveAsFlow()

    fun onIntent(intent: ChatContract.Intent) {
        when (intent) {
            ChatContract.Intent.Load -> load()
            is ChatContract.Intent.UpdateInput ->
                _state.update { it.copy(inputQuery = intent.value) }

            ChatContract.Intent.ApplySearch ->
                _state.update { it.copy(appliedQuery = it.inputQuery.trim()) }
        }
    }

    private fun load() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }

        runCatching { getChats() }
            .onSuccess { list -> _state.update { it.copy(isLoading = false, chats = list) } }
            .onFailure { e ->
                _state.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
                _effects.trySend(ChatContract.Effect.ShowToast("Failed to load chats"))
            }
    }
}
