package com.example.compose.ui.components.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.compose.R
import com.example.compose.navigation.Screen
import com.example.compose.ui.theme.AppTheme

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentRoute: String
) {
    NavigationBar(
        containerColor = AppTheme.colorScheme.surface,
        contentColor = AppTheme.colorScheme.onSurface
    ) {
        val colors = NavigationBarItemDefaults.colors(
            selectedIconColor = AppTheme.colorScheme.primary,
            unselectedIconColor = AppTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            indicatorColor = AppTheme.colorScheme.primary.copy(alpha = 0.1f)
        )

        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
            selected = currentRoute == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            },
            colors = colors
        )

        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite") },
            selected = currentRoute == Screen.Favorite.route,
            onClick = { navController.navigate(Screen.Favorite.route) },
            colors = colors
        )

        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.ic_message), contentDescription = "Chat") },
            selected = currentRoute == Screen.Chat.route,
            onClick = { navController.navigate(Screen.Chat.route) },
            colors = colors
        )

        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications") },
            selected = currentRoute == Screen.Notification.route,
            onClick = { navController.navigate(Screen.Notification.route) },
            colors = colors
        )
    }
}
