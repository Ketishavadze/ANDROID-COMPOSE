package com.example.compose.navigation

sealed class Screen(val route: String) {
    data object MyOrdersScreen : Screen("my_orders")
    data object OrderDetailsScreen : Screen("order_details/{orderId}") {
        fun create(orderId: String) = "order_details/$orderId"
    }
}
