package com.example.compose.domain.usecase

import com.example.compose.domain.repository.RegisterRepository
import javax.inject.Inject

class SubmitRegisterUseCase @Inject constructor(
    private val repo: RegisterRepository
) {
    suspend operator fun invoke(payload: com.example.compose.domain.model.RegisterPayload) =
        repo.submit(payload)
}