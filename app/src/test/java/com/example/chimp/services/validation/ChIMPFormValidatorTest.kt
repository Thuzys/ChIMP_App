package com.example.chimp.services.validation

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class ChIMPFormValidatorTest {

    @Test
    fun validate_username_with_valid_username_returns_success() = runBlocking {
        val formValidator = ChIMPFormValidator()
        val result = formValidator.validateUsername("Alice")
        assertTrue(result.isSuccess())
    }

    @Test
    fun validate_username_with_invalid_username_returns_failure() = runBlocking {
        val formValidator = ChIMPFormValidator()
        val result = formValidator.validateUsername("")
        assertTrue(result.isFailure())
    }

    @Test
    fun validate_username_without_upper_case_characters_returns_failure() = runBlocking {
        val formValidator = ChIMPFormValidator()
    val result = formValidator.validateUsername("alice")
        assertTrue(result.isFailure())
    }

    @Test
    fun validate_username_without_lower_case_characters_returns_failure() = runBlocking {
        val formValidator = ChIMPFormValidator()
        val result = formValidator.validateUsername("ALICE")
        assertTrue(result.isFailure())
    }

    @Test
    fun validate_password_with_valid_password_returns_success() = runBlocking {
        val formValidator = ChIMPFormValidator()
        val result = formValidator.validatePassword("@dminIsel123")
        assertTrue(result.isSuccess())
    }

    @Test
    fun validate_password_with_invalid_password_returns_failure() = runBlocking {
        val formValidator = ChIMPFormValidator()
        val result = formValidator.validatePassword("")
        assertTrue(result.isFailure())
    }

    @Test
    fun validate_password_with_password_shorter_than_8_characters_returns_failure() = runBlocking {
        val formValidator = ChIMPFormValidator()
        val result = formValidator.validatePassword("123")
        assertTrue(result.isFailure())
    }

    @Test
    fun validate_password_with_password_without_upper_case_characters_returns_failure() = runBlocking {
        val formValidator = ChIMPFormValidator()
        val result = formValidator.validatePassword("@dminisel123")
        assertTrue(result.isFailure())
    }

    @Test
    fun validate_password_with_password_without_special_characters_returns_failure() = runBlocking {
        val formValidator = ChIMPFormValidator()
        val result = formValidator.validatePassword("AdminIsel123")
        assertTrue(result.isFailure())
    }

    @Test
    fun validate_password_with_password_without_lower_case_characters_returns_failure() = runBlocking {
        val formValidator = ChIMPFormValidator()
        val result = formValidator.validatePassword("@DMINISEL123")
        assertTrue(result.isFailure())
    }

    @Test
    fun validate_password_with_password_without_numbers_returns_failure() = runBlocking {
        val formValidator = ChIMPFormValidator()
        val result = formValidator.validatePassword("AdminIsel")
        assertTrue(result.isFailure())
    }
}