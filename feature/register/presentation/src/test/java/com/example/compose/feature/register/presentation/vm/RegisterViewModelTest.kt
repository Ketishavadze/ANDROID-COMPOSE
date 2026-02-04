package com.example.compose.feature.register.presentation.vm

import app.cash.turbine.test
import com.example.compose.core.domain.common.Resource
import com.example.compose.feature.register.domain.model.FieldConfig
import com.example.compose.feature.register.domain.model.FieldType
import com.example.compose.feature.register.domain.model.KeyboardType
import com.example.compose.feature.register.domain.usecase.GetRegisterConfigUseCase
import com.example.compose.feature.register.domain.usecase.SubmitRegisterUseCase
import com.example.compose.feature.register.domain.validation.ValidateRegisterFields
import com.example.compose.feature.register.presentation.MainDispatcherRule
import com.example.compose.feature.register.presentation.contract.RegisterEvent
import com.example.compose.feature.register.presentation.contract.RegisterSideEffect
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import kotlin.collections.get

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

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