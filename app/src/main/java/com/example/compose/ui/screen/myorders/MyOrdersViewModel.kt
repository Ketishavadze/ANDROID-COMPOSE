package com.example.compose.ui.screen.myorders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.domain.usecase.GetOrdersUseCase
import com.example.compose.domain.usecase.RefreshOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyOrdersViewModel @Inject constructor(
    private val getOrders: GetOrdersUseCase,
    private val refreshOrders: RefreshOrdersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MyOrdersContract.State())
    val state: StateFlow<MyOrdersContract.State> = _state.asStateFlow()

    private val _effects = Channel<MyOrdersContract.Effect>(Channel.BUFFERED)
    val effects: Flow<MyOrdersContract.Effect> = _effects.receiveAsFlow()

    fun onIntent(intent: MyOrdersContract.Intent) {
        when (intent) {
            MyOrdersContract.Intent.Load -> load()
            MyOrdersContract.Intent.Refresh -> refresh()
            is MyOrdersContract.Intent.SelectStatus -> {
                _state.update { current -> current.copy(selectedStatus = intent.status) }
            }
        }
    }

    private fun load() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }

        runCatching { getOrders() }
            .onSuccess { list ->
                _state.update { it.copy(isLoading = false, orders = list) }
            }
            .onFailure { e ->
                _state.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
                _effects.trySend(MyOrdersContract.Effect.ShowToast("Failed to load orders"))
            }
    }

    private fun refresh() = viewModelScope.launch {
        _state.update { it.copy(isRefreshing = true, error = null) }

        runCatching { refreshOrders() }
            .onSuccess { list ->
                _state.update { it.copy(isRefreshing = false, orders = list) }
            }
            .onFailure { e ->
                _state.update { it.copy(isRefreshing = false, error = e.message ?: "Unknown error") }
                _effects.trySend(MyOrdersContract.Effect.ShowToast("Failed to refresh"))
            }
    }
}
