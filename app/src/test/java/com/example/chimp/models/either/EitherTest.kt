package com.example.chimp.models.either

import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import org.junit.Test

class EitherTest {
    @Test
    fun testSuccess() {
        val either = success("Hello")
        assert(either.isSuccess())
        assert(either.value == "Hello")
    }

    @Test
    fun testFailure() {
        val either = failure("Error")
        assert(either.isFailure())
        assert(either.value == "Error")
    }
}