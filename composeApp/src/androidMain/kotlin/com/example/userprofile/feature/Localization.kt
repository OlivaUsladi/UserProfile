package com.example.userprofile.feature

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.userprofile.model.domain.AppLanguage

class AppStrings(val currentLanguage: AppLanguage) {
    val profileTitle: String
        get() = when (currentLanguage) {
            AppLanguage.ENGLISH -> "User Profile"
            AppLanguage.RUSSIAN -> "Профиль пользователя"
        }

    val editProfile: String
        get() = when (currentLanguage) {
            AppLanguage.ENGLISH -> "Edit Profile"
            AppLanguage.RUSSIAN -> "Редактировать профиль"
        }

    val settings: String
        get() = when (currentLanguage) {
            AppLanguage.ENGLISH -> "Settings"
            AppLanguage.RUSSIAN -> "Настройки"
        }

    val logout: String
        get() = when (currentLanguage) {
            AppLanguage.ENGLISH -> "Logout"
            AppLanguage.RUSSIAN -> "Выйти из аккаунта"
        }

    val darkTheme: String
        get() = when (currentLanguage) {
            AppLanguage.ENGLISH -> "Dark Theme"
            AppLanguage.RUSSIAN -> "Тёмная тема"
        }

    val notifications: String
        get() = when (currentLanguage) {
            AppLanguage.ENGLISH -> "Notifications"
            AppLanguage.RUSSIAN -> "Уведомления"
        }

    val languageText: String
        get() = when (currentLanguage) {
            AppLanguage.ENGLISH -> "Language"
            AppLanguage.RUSSIAN -> "Язык"
        }

    val fontSize: String
        get() = when (currentLanguage) {
            AppLanguage.ENGLISH -> "Font Size"
            AppLanguage.RUSSIAN -> "Размер шрифта"
        }

    val save: String
        get() = when (currentLanguage) {
            AppLanguage.ENGLISH -> "Save"
            AppLanguage.RUSSIAN -> "Сохранить"
        }

    val name: String
        get() = when (currentLanguage) {
            AppLanguage.ENGLISH -> "Name"
            AppLanguage.RUSSIAN -> "Имя"
        }

    val email: String
        get() = when (currentLanguage) {
            AppLanguage.ENGLISH -> "Email"
            AppLanguage.RUSSIAN -> "Email"
        }

    val notSpecified: String
        get() = when (currentLanguage) {
            AppLanguage.ENGLISH -> "Not specified"
            AppLanguage.RUSSIAN -> "Не указано"
        }

    val logoutConfirmTitle: String
        get() = when (currentLanguage) {
            AppLanguage.ENGLISH -> "Logout"
            AppLanguage.RUSSIAN -> "Выход из аккаунта"
        }

    val logoutConfirmMessage: String
        get() = when (currentLanguage) {
            AppLanguage.ENGLISH -> "Are you sure you want to logout?"
            AppLanguage.RUSSIAN -> "Вы уверены, что хотите выйти?"
        }

    val cancel: String
        get() = when (currentLanguage) {
            AppLanguage.ENGLISH -> "Cancel"
            AppLanguage.RUSSIAN -> "Отмена"
        }

    val saveSettings: String
        get() = when (currentLanguage) {
            AppLanguage.ENGLISH -> "Save Settings"
            AppLanguage.RUSSIAN -> "Сохранить настройки"
        }

}

@Composable
fun rememberAppStrings(language: AppLanguage): AppStrings {
    return remember(language) { AppStrings(language) }
}