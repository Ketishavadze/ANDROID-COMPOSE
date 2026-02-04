package com.example.compose.feature.register.data.mapper

import com.example.compose.feature.register.data.remote.dto.FieldConfigDto
import com.example.compose.feature.register.domain.model.FieldType
import org.junit.Assert.*
import org.junit.Test

class FieldMapperTest {

    @Test
    fun `maps input`() {
        val dto = FieldConfigDto(1, "UserName", "input", "text", false, true, null)
        val d = dto.toDomain()
        assertEquals(FieldType.INPUT, d.fieldType)
        assertEquals(1, d.fieldId)
        assertEquals("UserName", d.hint)
    }

    @Test
    fun `maps chooser`() {
        val dto = FieldConfigDto(89, "Birthday", "chooser", null, false, true, null)
        val d = dto.toDomain()
        assertEquals(FieldType.CHOOSER, d.fieldType)
    }
}
