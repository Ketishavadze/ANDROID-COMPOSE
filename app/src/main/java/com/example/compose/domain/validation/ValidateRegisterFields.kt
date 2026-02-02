package com.example.compose.domain.validation


import com.example.compose.domain.model.FieldConfig

data class ValidationError(val fieldId: Int, val hint: String)

class ValidateRegisterFields {
    fun validate(
        config: List<List<FieldConfig>>,
        values: Map<Int, String>
    ): ValidationError? {
        val activeFields = config.flatten().filter { it.isActive }
        for (f in activeFields) {
            if (f.required) {
                val v = values[f.fieldId]?.trim().orEmpty()
                if (v.isBlank()) return ValidationError(f.fieldId, f.hint)
            }
        }
        return null
    }
}
