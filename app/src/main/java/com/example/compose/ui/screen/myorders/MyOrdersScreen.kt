@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.compose.ui.screen.myorders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.ui.components.OrderCard
import com.example.compose.ui.components.StatusChipsRow
import com.example.compose.ui.graphics.AppColors
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.ui.res.painterResource
import com.example.compose.R

@Composable
fun MyOrdersScreen(
    onOpenDetails: (String) -> Unit,
    vm: MyOrdersViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) { vm.onIntent(MyOrdersContract.Intent.Load) }

    LaunchedEffect(Unit) {
        vm.effects.collectLatest {
        }
    }

    val pullState = rememberPullToRefreshState()

    if (pullState.isRefreshing && !state.isRefreshing) {
        LaunchedEffect(Unit) { vm.onIntent(MyOrdersContract.Intent.Refresh) }
    }
    LaunchedEffect(state.isRefreshing) {
        if (!state.isRefreshing && pullState.isRefreshing) pullState.endRefresh()
    }

    val filtered = remember(state.orders, state.selectedStatus) {
        state.orders.filter { it.status == state.selectedStatus }
    }

    val pagePadding = 16.dp
    val cardSpacing = 14.dp

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Orders") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        @androidx.compose.runtime.Composable {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_menu),
                                contentDescription = "Menu" ,
                                tint = AppColors.TextPrimary
                            )

                        }
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                        painter = painterResource(id = R.drawable.ic_notification),
                        contentDescription = "Notifications",
                            tint = AppColors.TextPrimary
                        )
                    }
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .nestedScroll(pullState.nestedScrollConnection)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = pagePadding)
            ) {
                Spacer(Modifier.height(8.dp))

                StatusChipsRow(
                    selected = state.selectedStatus,
                    onSelect = { vm.onIntent(MyOrdersContract.Intent.SelectStatus(it)) }
                )

                Spacer(Modifier.height(12.dp))

                when {
                    state.isLoading -> {
                        LinearProgressIndicator(Modifier.fillMaxWidth())
                        Spacer(Modifier.height(12.dp))
                        Text("Loading…")
                    }

                    state.error != null -> {
                        Spacer(Modifier.height(8.dp))
                        Text("Error: ${state.error}")
                    }

                    filtered.isEmpty() -> {
                        Spacer(Modifier.height(24.dp))
                        Text("No orders found")
                    }

                    else -> {
                        LazyColumn(
                            contentPadding = PaddingValues(vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(cardSpacing),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(filtered, key = { it.id }) { order ->
                                OrderCard(
                                    order = order,
                                    onDetails = { onOpenDetails(order.id) }
                                )
                            }
                        }
                    }
                }
            }

            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = pullState
            )
        }
    }
}
