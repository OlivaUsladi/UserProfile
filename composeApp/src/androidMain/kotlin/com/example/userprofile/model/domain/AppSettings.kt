package com.example.userprofile.model.domain

data class AppSettings(
    val darkTheme: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val language: AppLanguage = AppLanguage.RUSSIAN,
    val fontSize: Float = 16f
)
