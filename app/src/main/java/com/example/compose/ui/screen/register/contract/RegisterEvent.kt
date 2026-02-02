package com.example.compose.ui.screen.register.contract

sealed interface RegisterEvent {
    data object Load : RegisterEvent
    data class OnValueChange(val fieldId: Int, val value: String) : RegisterEvent
    data object Submit : RegisterEvent
}