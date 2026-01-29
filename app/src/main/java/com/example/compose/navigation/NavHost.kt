package com.example.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.ui.screen.home.HomeScreen
import com.example.compose.ui.screen.favorite.FavoriteScreen
import com.example.compose.ui.screen.chat.ChatScreen
import com.example.compose.ui.screen.notification.NotificationScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
            )
        }

        composable(Screen.Favorite.route) {
            FavoriteScreen(
                navController = navController
            )
        }

        composable(Screen.Chat.route) {
            ChatScreen(
                navController = navController
            )
        }

        composable(Screen.Notification.route) {
            NotificationScreen(
                navController = navController
            )
        }
    }
}
