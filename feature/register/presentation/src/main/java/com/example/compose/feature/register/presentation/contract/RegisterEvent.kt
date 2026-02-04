package com.example.compose.feature.register.presentation.contract

sealed interface RegisterEvent {
    data object Load : RegisterEvent
    data class OnValueChange(val fieldId: Int, val value: String) : RegisterEvent
    data object Submit : RegisterEvent
}