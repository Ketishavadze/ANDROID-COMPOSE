package com.example.compose.ui.screen.orderdetails

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.domain.model.OrderStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsScreen(
    orderId: String,
    onBack: () -> Unit,
    vm: OrderDetailsViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(orderId) {
        vm.onIntent(OrderDetailsContract.Intent.Load(orderId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Text("<") }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            if (state.isLoading) {
                LinearProgressIndicator(Modifier.fillMaxWidth())
                return@Column
            }

            val order = state.order
            if (order == null) {
                Text(state.error ?: "No order")
                return@Column
            }

            Text("Order #${order.id}", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(12.dp))
            Text("Status: ${order.status.name}")
            Text("Date(ms): ${order.dateMillis}")

            Spacer(Modifier.height(24.dp))

            val canChange = order.status == OrderStatus.PENDING

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    enabled = canChange,
                    onClick = {
                        vm.onIntent(OrderDetailsContract.Intent.ChangeStatus(order.id, OrderStatus.DELIVERED))
                    }
                ) { Text("Mark Delivered") }

                OutlinedButton(
                    enabled = canChange,
                    onClick = {
                        vm.onIntent(OrderDetailsContract.Intent.ChangeStatus(order.id, OrderStatus.CANCELED))
                    }
                ) { Text("Cancel") }
            }

            state.error?.let {
                Spacer(Modifier.height(12.dp))
                Text("Error: $it")
            }
        }
    }
}
