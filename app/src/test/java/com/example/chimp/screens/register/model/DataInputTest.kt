package com.example.chimp.screens.register.model

import com.example.chimp.models.DataInput
import com.example.chimp.models.either.Success
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class DataInputTest {
    @Test
    fun a_DataInput_from_string_return_a_correct_input() {
        val input = "test"
        val dataInput = DataInput.fromString(input)
        assertEquals(input, dataInput.input)
    }

    @Test
    fun a_DataInput_from_string_return_a_correct_validation() {
        val input = "test"
        val dataInput = DataInput.fromString(input)
        assertTrue(dataInput.validation.isSuccess())
    }

    @Test
    fun a_DataInput_from_string_return_a_false_validation() {
        val input = ""
        val dataInput = DataInput.fromString(input)
        val validation = dataInput.validation as? Success<Boolean>
        assertFalse(validation?.value ?: true)
    }
}