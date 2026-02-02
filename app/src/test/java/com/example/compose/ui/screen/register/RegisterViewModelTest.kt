package com.example.compose.ui.screen.register

import app.cash.turbine.test
import com.example.compose.MainDispatcherRule
import com.example.compose.common.Resource
import com.example.compose.domain.model.*
import com.example.compose.domain.usecase.GetRegisterConfigUseCase
import com.example.compose.domain.usecase.SubmitRegisterUseCase
import com.example.compose.domain.validation.ValidateRegisterFields
import com.example.compose.ui.screen.register.contract.RegisterEvent
import com.example.compose.ui.screen.register.contract.RegisterSideEffect
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    @get:Rule val mainDispatcherRule = MainDispatcherRule()

    private val getConfig: GetRegisterConfigUseCase = mockk()
    private val submit: SubmitRegisterUseCase = mockk()
    private val validator = ValidateRegisterFields()

    @Test
    fun `submit with missing required emits Georgian error`() = runTest {
        val config = listOf(
            listOf(FieldConfig(2, "Email", FieldType.INPUT, KeyboardType.TEXT, true, true, null))
        )
        coEvery { getConfig() } returns Resource.Success(config)
        coEvery { submit(any()) } returns Resource.Success(Unit)

        val vm = RegisterViewModel(getConfig, submit, validator)

        vm.sideEffect.test {
            vm.onEvent(RegisterEvent.Load)
            advanceUntilIdle()

            vm.onEvent(RegisterEvent.Submit)
            advanceUntilIdle()

            val eff = awaitItem() as RegisterSideEffect.ShowMessage
            assertTrue(eff.message.contains("არ არის შევსბული ფილდი"))
            assertTrue(eff.message.contains("Email"))

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `submit success emits payload map`() = runTest {
        val config = listOf(
            listOf(FieldConfig(2, "Email", FieldType.INPUT, KeyboardType.TEXT, true, true, null))
        )
        coEvery { getConfig() } returns Resource.Success(config)
        coEvery { submit(any()) } returns Resource.Success(Unit)

        val vm = RegisterViewModel(getConfig, submit, validator)

        vm.sideEffect.test {
            vm.onEvent(RegisterEvent.Load)
            advanceUntilIdle()

            vm.onEvent(RegisterEvent.OnValueChange(2, "a@b.com"))
            vm.onEvent(RegisterEvent.Submit)
            advanceUntilIdle()

            val eff = awaitItem() as RegisterSideEffect.SubmitSuccess
            assertEquals("a@b.com", eff.payload[2])

            cancelAndIgnoreRemainingEvents()
        }
    }
}
