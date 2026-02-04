package com.example.compose.feature.register.presentation.contract

sealed interface RegisterSideEffect {
    data class ShowMessage(val message: String) : RegisterSideEffect
    data class SubmitSuccess(val payload: Map<Int, String>) : RegisterSideEffect
}