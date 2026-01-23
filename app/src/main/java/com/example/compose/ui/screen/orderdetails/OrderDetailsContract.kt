package com.example.compose.ui.screen.orderdetails


import com.example.compose.domain.model.Order
import com.example.compose.domain.model.OrderStatus

object OrderDetailsContract {
    data class State(
        val isLoading: Boolean = false,
        val order: Order? = null,
        val error: String? = null
    )

    sealed interface Intent {
        data class Load(val id: String) : Intent
        data class ChangeStatus(val id: String, val newStatus: OrderStatus) : Intent
    }
}
