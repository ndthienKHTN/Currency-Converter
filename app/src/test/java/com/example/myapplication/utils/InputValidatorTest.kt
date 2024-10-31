package com.example.myapplication.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class InputValidatorTest {

    @Test
    fun validateTextField_withNonEmptyInput_returnsTrue() {
        assertTrue(InputValidator.validateTextField("123"))
    }

    @Test
    fun validateTextField_withEmptyInput_returnsFalse() {
        assertFalse(InputValidator.validateTextField(""))
    }

    @Test
    fun validateDropdown_withSelectedRate_returnsTrue() {
        assertTrue(InputValidator.validateDropdown(Pair("USD", 1.0)))
    }

    @Test
    fun validateDropdown_withNullSelectedRate_returnsFalse() {
        assertFalse(InputValidator.validateDropdown(null))
    }
}