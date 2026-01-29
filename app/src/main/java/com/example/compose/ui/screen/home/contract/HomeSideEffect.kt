package com.example.compose.ui.screen.home.contract

sealed class HomeSideEffect {
    data class ShowError(val message: String) : HomeSideEffect()
}