package com.example.compose.ui.screen.myorders.viewmodel

import com.example.compose.domain.model.Order
import com.example.compose.domain.model.OrderStatus
import com.example.compose.domain.usecase.GetOrdersUseCase
import com.example.compose.domain.usecase.RefreshOrdersUseCase
import com.example.compose.ui.screen.myorders.MyOrdersContract
import com.example.compose.ui.screen.myorders.MyOrdersViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MyOrdersViewModelTest {

    private lateinit var viewModel: MyOrdersViewModel
    private lateinit var getOrdersUseCase: GetOrdersUseCase
    private lateinit var refreshOrdersUseCase: RefreshOrdersUseCase

    private val testDispatcher = StandardTestDispatcher()

    private val mockOrders = listOf(
        Order(
            id = "1",
            orderNumber = "1524",
            dateMillis = 1715558400000,
            trackingNumber = "IK287368838",
            quantity = 2,
            subtotal = 110.0,
            status = OrderStatus.PENDING
        ),
        Order(
            id = "2",
            orderNumber = "1829",
            dateMillis = 1715644800000,
            trackingNumber = "IK287368839",
            quantity = 1,
            subtotal = 45.0,
            status = OrderStatus.DELIVERED
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        getOrdersUseCase = mockk()
        refreshOrdersUseCase = mockk()

        viewModel = MyOrdersViewModel(
            getOrders = getOrdersUseCase,
            refreshOrders = refreshOrdersUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load orders updates state`() = runTest {
        coEvery { getOrdersUseCase() } returns mockOrders

        viewModel.onIntent(MyOrdersContract.Intent.Load)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(mockOrders, state.orders)
        assertFalse(state.isLoading)
        assertNull(state.error)

        coVerify(exactly = 1) { getOrdersUseCase() }
    }

    @Test
    fun `load orders failure sets error and stops loading`() = runTest {
        coEvery { getOrdersUseCase() } throws RuntimeException("network error")

        viewModel.onIntent(MyOrdersContract.Intent.Load)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals("network error", state.error)

        coVerify(exactly = 1) { getOrdersUseCase() }
    }

    @Test
    fun `refresh orders updates state and stops refreshing`() = runTest {
        coEvery { refreshOrdersUseCase() } returns mockOrders

        viewModel.onIntent(MyOrdersContract.Intent.Refresh)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(mockOrders, state.orders)
        assertFalse(state.isRefreshing)
        assertNull(state.error)

        coVerify(exactly = 1) { refreshOrdersUseCase() }
    }

    @Test
    fun `refresh orders failure sets error and stops refreshing`() = runTest {
        coEvery { refreshOrdersUseCase() } throws RuntimeException("refresh failed")

        viewModel.onIntent(MyOrdersContract.Intent.Refresh)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isRefreshing)
        assertEquals("refresh failed", state.error)

        coVerify(exactly = 1) { refreshOrdersUseCase() }
    }

    @Test
    fun `select status updates selectedStatus`() = runTest {
        val initial = viewModel.state.value.selectedStatus

        viewModel.onIntent(MyOrdersContract.Intent.SelectStatus(OrderStatus.CANCELED))
        advanceUntilIdle()

        val updated = viewModel.state.value.selectedStatus
        assertNotEquals(initial, updated)
        assertEquals(OrderStatus.CANCELED, updated)
    }
}
