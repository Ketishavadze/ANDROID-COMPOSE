package com.example.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.ui.screen.myorders.MyOrdersScreen
import com.example.compose.ui.screen.orderdetails.OrderDetailsScreen

@Composable
fun AppNavHost() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Screen.MyOrdersScreen.route) {
        composable(Screen.MyOrdersScreen.route) {
            MyOrdersScreen(onOpenDetails = { id -> nav.navigate(Screen.OrderDetailsScreen.create(id)) })
        }
        composable(Screen.OrderDetailsScreen.route) { backStack ->
            val id = backStack.arguments?.getString("orderId").orEmpty()
            OrderDetailsScreen(orderId = id, onBack = { nav.popBackStack() })
        }
    }
}
