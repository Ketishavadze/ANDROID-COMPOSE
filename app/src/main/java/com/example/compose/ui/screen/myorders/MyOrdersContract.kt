package com.example.compose.ui.screen.myorders


import com.example.compose.domain.model.Order
import com.example.compose.domain.model.OrderStatus

object MyOrdersContract {

    data class State(
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false,
        val selectedStatus: OrderStatus = OrderStatus.PENDING,
        val orders: List<Order> = emptyList(),
        val error: String? = null
    )

    sealed interface Intent {
        data object Load : Intent
        data object Refresh : Intent
        data class SelectStatus(val status: OrderStatus) : Intent
    }

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
    }
}
