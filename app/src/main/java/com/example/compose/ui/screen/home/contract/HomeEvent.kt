package com.example.compose.ui.screen.home.contract

sealed class HomeEvent {
    object LoadInitial : HomeEvent()
    object Refresh : HomeEvent()
}