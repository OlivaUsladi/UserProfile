package com.example.userprofile.feature.domain

import com.example.userprofile.model.domain.AppSettings
import com.example.userprofile.model.repository.SettingsRepository

class SaveSettingsUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(settings: AppSettings) {
        settingsRepository.saveSettings(settings)
    }
}