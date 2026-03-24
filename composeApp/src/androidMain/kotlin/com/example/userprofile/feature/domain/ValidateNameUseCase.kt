package com.example.userprofile.feature.domain

class ValidateNameUseCase {

    operator fun invoke(name: String): ValidationResult {
        return when {
            name.isBlank() -> ValidationResult.Error("Имя не может быть пустым")
            name.length < 2 -> ValidationResult.Error("Имя должно содержать минимум 2 символа")
            else -> ValidationResult.Success
        }
    }

    sealed class ValidationResult {
        object Success : ValidationResult()

        data class Error(val message: String) : ValidationResult()
    }
}