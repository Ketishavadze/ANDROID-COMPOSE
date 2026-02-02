package com.example.compose.ui.screen.register

import androidx.lifecycle.viewModelScope
import com.example.compose.common.Resource
import com.example.compose.domain.model.RegisterPayload
import com.example.compose.domain.usecase.GetRegisterConfigUseCase
import com.example.compose.domain.usecase.SubmitRegisterUseCase
import com.example.compose.domain.validation.ValidateRegisterFields
import com.example.compose.ui.screen.common.BaseViewModel
import com.example.compose.ui.screen.register.contract.RegisterEvent
import com.example.compose.ui.screen.register.contract.RegisterSideEffect
import com.example.compose.ui.screen.register.contract.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val getConfig: GetRegisterConfigUseCase,
    private val submit: SubmitRegisterUseCase,
    private val validator: ValidateRegisterFields
) : BaseViewModel<RegisterState, RegisterEvent, RegisterSideEffect>(RegisterState()) {

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.Load -> load()
            is RegisterEvent.OnValueChange -> updateState {
                it.copy(values = it.values.toMutableMap().apply {
                    this[event.fieldId] = event.value
                })
            }
            RegisterEvent.Submit -> onSubmit()
        }
    }

    private fun load() = viewModelScope.launch {
        updateState { it.copy(isLoading = true) }
        when (val res = getConfig()) {
            is Resource.Success -> updateState { it.copy(config = res.data ?: emptyList(), isLoading = false) }
            is Resource.Error -> {
                emitSideEffect(RegisterSideEffect.ShowMessage(res.errorMessage ?: "Error"))
                updateState { it.copy(isLoading = false) }
            }
            else -> updateState { it.copy(isLoading = false) }
        }
    }

    private fun onSubmit() = viewModelScope.launch {
        val st = state.value
        val err = validator.validate(st.config, st.values)
        if (err != null) {
            emitSideEffect(RegisterSideEffect.ShowMessage("არ არის შევსბული ფილდი (${err.hint})"))
            return@launch
        }

        val payload = RegisterPayload(st.values)
        when (val res = submit(payload)) {
            is Resource.Success -> emitSideEffect(RegisterSideEffect.SubmitSuccess(st.values))
            is Resource.Error -> emitSideEffect(RegisterSideEffect.ShowMessage(res.errorMessage ?: "Error"))
            else -> {}
        }
    }
}
