package com.example.compose.data.repository

import com.example.compose.data.remote.common.Resource
import com.example.compose.data.remote.api.RegisterApiService
import com.example.compose.data.remote.dto.FieldConfigDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class RegisterRepositoryImplTest {

    @Test
    fun `getRegisterConfig maps dto`() = runTest {
        val api = mockk<RegisterApiService>()
        coEvery { api.getConfig() } returns listOf(
            listOf(FieldConfigDto(1, "UserName", "input", "text", false, true, null))
        )

        val repo = RegisterRepositoryImpl(api)
        val res = repo.getRegisterConfig()

        assertTrue(res is Resource.Success)
        val config = (res as Resource.Success).data!!
        assertEquals(1, config.size)
        assertEquals("UserName", config[0][0].hint)
    }
}
