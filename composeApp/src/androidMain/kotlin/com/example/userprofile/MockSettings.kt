package com.example.userprofile

import com.example.userprofile.model.domain.AppLanguage
import com.example.userprofile.model.domain.AppSettings
import com.example.userprofile.model.domain.UserProfile
import com.example.userprofile.model.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update

class MockSettingsRepository : SettingsRepository {

    private var _settings = MutableStateFlow(
        AppSettings(
            darkTheme = false,
            notificationsEnabled = true,
            language = AppLanguage.RUSSIAN,
            fontSize = 16f
        )
    )

    override fun getSettings(): Flow<AppSettings> = _settings.asStateFlow()

    override suspend fun saveSettings(settings: AppSettings) {
        _settings.value = settings
    }

    override suspend fun updateDarkTheme(enabled: Boolean) {
        _settings.update { it.copy(darkTheme = enabled) }
    }

    override suspend fun updateNotifications(enabled: Boolean) {
        _settings.update { it.copy(notificationsEnabled = enabled) }
    }

    override suspend fun updateLanguage(language: AppLanguage) {
        _settings.update { it.copy(language = language) }
    }

    override suspend fun updateFontSize(size: Float) {
        _settings.update { it.copy(fontSize = size) }
    }

    override suspend fun saveUserProfile(profile: UserProfile) {
    }

    override fun getUserProfile(): Flow<UserProfile> = flowOf(UserProfile())

    fun getCurrentSettings(): AppSettings = _settings.value
}