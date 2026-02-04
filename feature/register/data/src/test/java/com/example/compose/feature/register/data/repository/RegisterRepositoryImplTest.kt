package com.example.compose.feature.register.data.repository

import com.example.compose.core.domain.common.Resource
import com.example.compose.feature.register.data.remote.api.RegisterApiService
import com.example.compose.feature.register.data.remote.dto.FieldConfigDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import kotlin.collections.get

class RegisterRepositoryImplTest {

    @Test
    fun `getRegisterConfig maps dto`() = runTest {
        val api = mockk<RegisterApiService>()
        coEvery { api.getConfig() } returns listOf(
            listOf(FieldConfigDto(1, "UserName", "input", "text", false, true, null))
        )

        val repo = RegisterRepositoryImpl(api)
        val res = repo.getRegisterConfig()

        Assert.assertTrue(res is Resource.Success)
        val config = (res as Resource.Success).data!!
        Assert.assertEquals(1, config.size)
        Assert.assertEquals("UserName", config[0][0].hint)
    }
}