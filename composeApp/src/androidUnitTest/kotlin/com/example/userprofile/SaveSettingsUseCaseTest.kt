package com.example.userprofile

import com.example.userprofile.MockSettingsRepository
import com.example.userprofile.feature.domain.SaveSettingsUseCase
import com.example.userprofile.model.domain.AppLanguage
import com.example.userprofile.model.domain.AppSettings
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SaveSettingsUseCaseTest {

    @Test
    fun `should save settings to repository`() = runTest {

        val mockRepository = MockSettingsRepository()
        val saveSettingsUseCase = SaveSettingsUseCase(mockRepository)

        val newSettings = AppSettings(
            darkTheme = true,
            notificationsEnabled = false,
            language = AppLanguage.ENGLISH,
            fontSize = 20f
        )

        saveSettingsUseCase(newSettings)

        val savedSettings = mockRepository.getCurrentSettings()
        assertEquals(newSettings.darkTheme, savedSettings.darkTheme)
        assertEquals(newSettings.notificationsEnabled, savedSettings.notificationsEnabled)
        assertEquals(newSettings.language, savedSettings.language)
        assertEquals(newSettings.fontSize, savedSettings.fontSize)
    }

    @Test
    fun `should override existing settings`() = runTest {

        val mockRepository = MockSettingsRepository()
        val saveSettingsUseCase = SaveSettingsUseCase(mockRepository)

        val firstSettings = AppSettings(darkTheme = false, fontSize = 16f)
        val secondSettings = AppSettings(darkTheme = true, fontSize = 20f)

        saveSettingsUseCase(firstSettings)
        saveSettingsUseCase(secondSettings)

        val savedSettings = mockRepository.getCurrentSettings()
        assertEquals(true, savedSettings.darkTheme)
        assertEquals(20f, savedSettings.fontSize)
    }
}