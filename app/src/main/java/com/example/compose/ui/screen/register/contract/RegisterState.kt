package com.example.compose.ui.screen.register.contract

import com.example.compose.domain.model.RegisterConfig

data class RegisterState(
    val isLoading: Boolean = false,
    val config: RegisterConfig = emptyList(),
    val values: Map<Int, String> = emptyMap()
)