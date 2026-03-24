package com.example.userprofile.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userprofile.feature.domain.LoadProfileUseCase
import com.example.userprofile.feature.domain.SaveSettingsUseCase
import com.example.userprofile.feature.domain.ValidateNameUseCase
import com.example.userprofile.model.domain.AppLanguage
import com.example.userprofile.model.domain.AppSettings
import com.example.userprofile.model.domain.UserProfile
import com.example.userprofile.model.repository.SettingsRepository
import com.example.userprofile.model.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val loadProfileUseCase: LoadProfileUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val userRepository: UserRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        loadProfile()
        loadSettings()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                loadProfileUseCase().collect { profile ->
                    _state.update { currentState ->
                        currentState.copy(
                            userProfile = profile,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Ошибка загрузки профиля: ${e.message}"
                    )
                }
            }
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            try {
                settingsRepository.getSettings().collect { settings ->
                    _state.update { currentState ->
                        currentState.copy(settings = settings)
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Ошибка загрузки настроек: ${e.message}"
                    )
                }
            }
        }
    }

    fun updateName(name: String) {
        val validationResult = validateNameUseCase(name)
        val errorMessage = when (validationResult) {
            is ValidateNameUseCase.ValidationResult.Error -> validationResult.message
            else -> null
        }

        _state.update { currentState ->
            currentState.copy(
                userProfile = currentState.userProfile.copy(name = name),
                nameError = errorMessage
            )
        }
    }

    fun updateEmail(email: String) {
        _state.update { currentState ->
            currentState.copy(
                userProfile = currentState.userProfile.copy(email = email)
            )
        }
    }

    fun updateDarkTheme(enabled: Boolean) {
        _state.update { currentState ->
            currentState.copy(
                settings = currentState.settings.copy(darkTheme = enabled)
            )
        }
        if (_state.value.settings.notificationsEnabled) {
            showSnackbarMessage(
                if (_state.value.settings.language == AppLanguage.ENGLISH)
                    "Theme changed to ${if (enabled) "Dark" else "Light"}"
                else
                    "Тема изменена на ${if (enabled) "Тёмную" else "Светлую"}"
            )
        }
    }

    fun updateNotifications(enabled: Boolean) {
        _state.update { currentState ->
            currentState.copy(
                settings = currentState.settings.copy(notificationsEnabled = enabled)
            )
        }
        showSnackbarMessage(
            if (_state.value.settings.language == AppLanguage.ENGLISH)
                if (enabled) "Notifications enabled" else "Notifications disabled"
            else
                if (enabled) "Уведомления включены" else "Уведомления выключены"
        )
    }

    fun updateLanguage(language: AppLanguage) {
        val oldLanguage = _state.value.settings.language
        _state.update { currentState ->
            currentState.copy(
                settings = currentState.settings.copy(language = language)
            )
        }
        if (_state.value.settings.notificationsEnabled) {
            showSnackbarMessage(
                if (language == AppLanguage.ENGLISH)
                    "Language changed to English"
                else
                    "Язык изменён на Русский"
            )
        }
    }

    fun updateFontSize(size: Float) {
        _state.update { currentState ->
            currentState.copy(
                settings = currentState.settings.copy(fontSize = size)
            )
        }
        if (_state.value.settings.notificationsEnabled) {
            showSnackbarMessage(
                if (_state.value.settings.language == AppLanguage.ENGLISH)
                    "Font size changed to ${size.toInt()}px"
                else
                    "Размер шрифта изменён на ${size.toInt()}px"
            )
        }
    }

    fun saveSettings() {

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            try {
                userRepository.updateUserProfile(_state.value.userProfile)
                saveSettingsUseCase(_state.value.settings)
                _state.update { it.copy(isSaving = false, error = null) }

                if (_state.value.settings.notificationsEnabled) {
                    showSnackbarMessage(
                        if (_state.value.settings.language == AppLanguage.ENGLISH)
                            "Settings saved successfully!"
                        else
                            "Настройки успешно сохранены!"
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isSaving = false,
                        error = "Ошибка сохранения: ${e.message}"
                    )
                }
            }
        }
    }

    fun showLogoutDialog() {
        _state.update { it.copy(showLogoutDialog = true) }
    }

    fun hideLogoutDialog() {
        _state.update { it.copy(showLogoutDialog = false) }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                userRepository.logout()
                _state.update {
                    it.copy(
                        userProfile = UserProfile(),
                        showLogoutDialog = false
                    )
                }

                if (_state.value.settings.notificationsEnabled) {
                    showSnackbarMessage(
                        if (_state.value.settings.language == AppLanguage.ENGLISH)
                            "Logged out successfully"
                        else
                            "Вы успешно вышли из аккаунта"
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Ошибка выхода: ${e.message}") }
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }


    fun showSnackbarMessage(message: String) {
        _state.update { it.copy(snackbarMessage = message) }
        viewModelScope.launch {
            kotlinx.coroutines.delay(3000)
            _state.update { it.copy(snackbarMessage = null) }
        }
    }

    fun clearSnackbarMessage() {
        _state.update { it.copy(snackbarMessage = null) }
    }
}