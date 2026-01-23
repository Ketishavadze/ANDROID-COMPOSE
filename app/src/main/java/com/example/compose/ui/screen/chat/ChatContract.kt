package com.example.compose.ui.screen.chat

import com.example.compose.domain.model.Chat

object ChatContract {

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,

        val inputQuery: String = "",

        val appliedQuery: String = "",

        val chats: List<Chat> = emptyList()
    )

    sealed interface Intent {
        data object Load : Intent
        data class UpdateInput(val value: String) : Intent
        data object ApplySearch : Intent
    }

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
    }
}
