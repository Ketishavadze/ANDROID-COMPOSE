package com.example.compose.domain.validation

import com.example.compose.domain.model.*
import org.junit.Assert.*
import org.junit.Test

class ValidateRegisterFieldsTest {

    @Test
    fun `required empty returns error with hint`() {
        val config = listOf(
            listOf(
                FieldConfig(2, "Email", FieldType.INPUT, KeyboardType.TEXT, required = true, isActive = true, iconUrl = null)
            )
        )
        val values = emptyMap<Int, String>()
        val err = ValidateRegisterFields().validate(config, values)
        assertNotNull(err)
        assertEquals(2, err!!.fieldId)
        assertEquals("Email", err.hint)
    }

    @Test
    fun `required filled passes`() {
        val config = listOf(
            listOf(FieldConfig(2, "Email", FieldType.INPUT, KeyboardType.TEXT, true, true, null))
        )
        val values = mapOf(2 to "a@b.com")
        val err = ValidateRegisterFields().validate(config, values)
        assertNull(err)
    }
}
