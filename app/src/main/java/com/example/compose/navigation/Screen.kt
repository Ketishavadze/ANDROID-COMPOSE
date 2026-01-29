package com.example.compose.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Chat : Screen("chat")
    object Notification : Screen("notification")
}