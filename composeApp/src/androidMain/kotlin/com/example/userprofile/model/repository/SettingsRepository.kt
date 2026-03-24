package com.example.userprofile.model.repository

import com.example.userprofile.model.domain.AppLanguage
import com.example.userprofile.model.domain.AppSettings
import com.example.userprofile.model.domain.UserProfile
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<AppSettings>
    suspend fun saveSettings(settings: AppSettings)
    suspend fun updateDarkTheme(enabled: Boolean)
    suspend fun updateNotifications(enabled: Boolean)
    suspend fun updateLanguage(language: AppLanguage)
    suspend fun updateFontSize(size: Float)

    suspend fun saveUserProfile(profile: UserProfile)
    fun getUserProfile(): Flow<UserProfile>
    
}