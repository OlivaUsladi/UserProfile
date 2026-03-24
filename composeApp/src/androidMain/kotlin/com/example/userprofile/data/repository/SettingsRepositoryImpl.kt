package com.example.userprofile.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.userprofile.model.domain.AppLanguage
import com.example.userprofile.model.domain.AppSettings
import com.example.userprofile.model.domain.UserProfile
import com.example.userprofile.model.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

class SettingsRepositoryImpl(private val context: Context) : SettingsRepository {

    companion object {
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
        private val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications")
        private val LANGUAGE_KEY = stringPreferencesKey("language")
        private val FONT_SIZE_KEY = floatPreferencesKey("font_size")


        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
    }

    override fun getSettings(): Flow<AppSettings> {
        return context.dataStore.data.map { preferences ->
            AppSettings(
                darkTheme = preferences[DARK_THEME_KEY] ?: false,
                notificationsEnabled = preferences[NOTIFICATIONS_KEY] ?: true,
                language = when (preferences[LANGUAGE_KEY]) {
                    "ru" -> AppLanguage.RUSSIAN
                    else -> AppLanguage.ENGLISH
                },
                fontSize = preferences[FONT_SIZE_KEY] ?: 16f
            )
        }
    }

    override suspend fun saveSettings(settings: AppSettings) {
        context.dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = settings.darkTheme
            preferences[NOTIFICATIONS_KEY] = settings.notificationsEnabled
            preferences[LANGUAGE_KEY] = settings.language.code
            preferences[FONT_SIZE_KEY] = settings.fontSize
        }
    }

    override suspend fun updateDarkTheme(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = enabled
        }
    }

    override suspend fun updateNotifications(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_KEY] = enabled
        }
    }

    override suspend fun updateLanguage(language: AppLanguage) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language.code
        }
    }

    override suspend fun updateFontSize(size: Float) {
        context.dataStore.edit { preferences ->
            preferences[FONT_SIZE_KEY] = size
        }
    }

    override suspend fun saveUserProfile(profile: UserProfile) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = profile.id
            preferences[USER_NAME_KEY] = profile.name
            preferences[USER_EMAIL_KEY] = profile.email
        }
    }

    override fun getUserProfile(): Flow<UserProfile> {
        return context.dataStore.data.map { preferences ->
            UserProfile(
                id = preferences[USER_ID_KEY] ?: "",
                name = preferences[USER_NAME_KEY] ?: "",
                email = preferences[USER_EMAIL_KEY] ?: ""
            )
        }
    }
}