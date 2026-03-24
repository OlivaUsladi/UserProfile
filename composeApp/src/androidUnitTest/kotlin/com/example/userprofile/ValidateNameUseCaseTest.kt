package com.example.userprofile

import com.example.userprofile.feature.domain.ValidateNameUseCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ValidateNameUseCaseTest {

    private val validateNameUseCase = ValidateNameUseCase()

    // Тест 1: правильное имя должно проходить валидацию
    @Test
    fun `should return success for valid name`() {
        val name = "Иван"

        val result = validateNameUseCase(name)

        assertTrue(result is ValidateNameUseCase.ValidationResult.Success)
    }

    // Тест 2: пустое имя должно возвращать ошибку
    @Test
    fun `should return error for empty name`() {
        val name = ""

        val result = validateNameUseCase(name)

        assertTrue(result is ValidateNameUseCase.ValidationResult.Error)
        assertEquals(
            "Имя не может быть пустым",
            (result as ValidateNameUseCase.ValidationResult.Error).message
        )
    }

    // Тест 3: имя из одного символа должно возвращать ошибку
    @Test
    fun `should return error for name with one character`() {
        val name = "A"

        val result = validateNameUseCase(name)

        assertTrue(result is ValidateNameUseCase.ValidationResult.Error)
        assertEquals(
            "Имя должно содержать минимум 2 символа",
            (result as ValidateNameUseCase.ValidationResult.Error).message
        )
    }

    // Тест 4: имя из пробелов должно возвращать ошибку
    @Test
    fun `should return error for name with only spaces`() {
        val name = "   "

        val result = validateNameUseCase(name)

        assertTrue(result is ValidateNameUseCase.ValidationResult.Error)
        assertEquals(
            "Имя не может быть пустым",
            (result as ValidateNameUseCase.ValidationResult.Error).message
        )
    }
}
