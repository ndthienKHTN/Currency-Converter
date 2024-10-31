package com.example.myapplication.utils

object InputValidator {
    fun validateTextField(input: String): Boolean {
        return input.isNotEmpty()
    }

    fun validateDropdown(selectedRate: Pair<String, Double>?): Boolean {
        return selectedRate != null
    }
}