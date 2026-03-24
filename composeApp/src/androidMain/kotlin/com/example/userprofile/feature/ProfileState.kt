package com.example.userprofile.feature

import com.example.userprofile.model.domain.AppSettings
import com.example.userprofile.model.domain.UserProfile

data class ProfileState(
    val isLoading: Boolean = false,
    val userProfile: UserProfile = UserProfile(),
    val settings: AppSettings = AppSettings(),
    val nameError: String? = null,
    val showLogoutDialog: Boolean = false,
    val error: String? = null,
    val isSaving: Boolean = false,
    val snackbarMessage: String? = null
)