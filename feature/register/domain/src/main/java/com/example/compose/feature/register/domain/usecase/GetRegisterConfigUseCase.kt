package com.example.compose.feature.register.domain.usecase

import com.example.compose.feature.register.domain.repository.RegisterRepository
import javax.inject.Inject

class GetRegisterConfigUseCase @Inject constructor(
    private val repo: RegisterRepository
) {
    suspend operator fun invoke() = repo.getRegisterConfig()
}