package com.example.chimp.models

import com.example.chimp.models.either.Success
import com.example.chimp.models.either.success
import com.example.chimp.screens.register.model.InputValidation

/**
 * Represents a data input.
 *
 * @property input the input value
 * @property validation the validation state of the input
 */
data class DataInput (
    val input: String,
    val validation: InputValidation
) {

    val isValid = validation is Success<Boolean> && validation.value

    companion object {
        /**
         * The initial state of the data input.
         */
        val initialState = DataInput("", success(false))

        /**
         * Creates a data input from a string.
         *
         * @param input the input string
         */
        fun fromString(input: String): DataInput = DataInput(input, success(false))
    }
}