package com.example.compose.feature.register.domain.usecase

import com.example.compose.feature.register.domain.repository.RegisterRepository
import javax.inject.Inject

class SubmitRegisterUseCase @Inject constructor(
    private val repo: RegisterRepository
) {
    suspend operator fun invoke(payload: com.example.compose.feature.register.domain.model.RegisterPayload) =
        repo.submit(payload)
}