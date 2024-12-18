package com.example.chimp.screens.register.model

import com.example.chimp.models.either.Either

/**
 * Represents a data input validation.
 *
 *
 * @property String the error message.
 * @property Boolean if is a valid input.
 *
 * @see Either
 */
internal typealias InputValidation = Either<String, Boolean>
