package com.example.compose.ui.screen.orderdetails


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.domain.model.OrderStatus
import com.example.compose.domain.usecase.GetOrderByIdUseCase
import com.example.compose.domain.usecase.UpdateOrderStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val getOrderById: GetOrderByIdUseCase,
    private val updateOrderStatus: UpdateOrderStatusUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OrderDetailsContract.State())
    val state: StateFlow<OrderDetailsContract.State> = _state

    fun onIntent(intent: OrderDetailsContract.Intent) {
        when (intent) {
            is OrderDetailsContract.Intent.Load -> load(intent.id)
            is OrderDetailsContract.Intent.ChangeStatus -> changeStatus(intent.id, intent.newStatus)
        }
    }

    private fun load(id: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }

        runCatching { getOrderById(id) }
            .onSuccess { order ->
                _state.update { state ->
                    state.copy(isLoading = false, order = order, error = null)
                }
            }
            .onFailure { e ->
                _state.update { state ->
                    state.copy(isLoading = false, error = e.message)
                }
            }
    }


    private fun changeStatus(id: String, newStatus: OrderStatus) = viewModelScope.launch {
        val current = _state.value.order ?: return@launch

        val allowed = current.status == OrderStatus.PENDING &&
                (newStatus == OrderStatus.DELIVERED || newStatus == OrderStatus.CANCELED)

        if (!allowed) return@launch

        runCatching { updateOrderStatus(id, newStatus) }
            .onSuccess { updated -> _state.update { it.copy(order = updated) } }
            .onFailure { e -> _state.update { it.copy(error = e.message) } }
    }
}
