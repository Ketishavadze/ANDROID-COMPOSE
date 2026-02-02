package com.example.compose.ui.screen.register

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.ui.screen.register.contract.RegisterEvent
import com.example.compose.ui.screen.register.contract.RegisterSideEffect

@Composable
fun RegisterRoute(
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var message by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) { viewModel.onEvent(RegisterEvent.Load) }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { eff ->
            when (eff) {
                is RegisterSideEffect.ShowMessage -> message = eff.message
                is RegisterSideEffect.SubmitSuccess -> message = "Submitted: ${eff.payload}"
            }
        }
    }

    RegisterScreen(
        state = state,
        message = message,
        onEvent = viewModel::onEvent
    )
}