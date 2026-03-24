package com.example.userprofile

import com.example.userprofile.feature.ProfileViewModel
import com.example.userprofile.feature.domain.LoadProfileUseCase
import com.example.userprofile.feature.domain.SaveSettingsUseCase
import com.example.userprofile.feature.domain.ValidateNameUseCase
import com.example.userprofile.model.domain.AppLanguage
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlin.test.BeforeTest
import kotlin.test.Test


class ProfileViewModelTest {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var mockUserRepository: MockUserRepository
    private lateinit var mockSettingsRepository: MockSettingsRepository
    private lateinit var loadProfileUseCase: LoadProfileUseCase
    private lateinit var saveSettingsUseCase: SaveSettingsUseCase
    private lateinit var validateNameUseCase: ValidateNameUseCase

    @BeforeTest
    fun setup() {

        mockUserRepository = MockUserRepository()
        mockSettingsRepository = MockSettingsRepository()

        loadProfileUseCase = LoadProfileUseCase(mockUserRepository)
        saveSettingsUseCase = SaveSettingsUseCase(mockSettingsRepository)
        validateNameUseCase = ValidateNameUseCase()

        viewModel = ProfileViewModel(
            loadProfileUseCase = loadProfileUseCase,
            saveSettingsUseCase = saveSettingsUseCase,
            validateNameUseCase = validateNameUseCase,
            userRepository = mockUserRepository,
            settingsRepository = mockSettingsRepository
        )
    }

    //Смена темы
    @Test
    fun `should toggle dark theme setting`() {

        val initialState = viewModel.state.value.settings.darkTheme

        viewModel.updateDarkTheme(!initialState)

        val newState = viewModel.state.value
        assertEquals(!initialState, newState.settings.darkTheme)
    }

    //Смена шрифта
    @Test
    fun `should change font size setting`() {
        val newSize = 20f

        viewModel.updateFontSize(newSize)

        val newState = viewModel.state.value
        assertEquals(newSize, newState.settings.fontSize)
    }

    //Изменение языка
    @Test
    fun `should change language setting`() {
        val initialState = viewModel.state.value.settings.language
        val newLanguage = if (initialState == AppLanguage.RUSSIAN) {
            AppLanguage.ENGLISH
        } else {
            AppLanguage.RUSSIAN
        }

        viewModel.updateLanguage(newLanguage)

        val newState = viewModel.state.value
        assertEquals(newLanguage, newState.settings.language)
    }

    //Состояние диалогового окна
    @Test
    fun `should show logout confirmation dialog`() {

        viewModel.showLogoutDialog()

        val state = viewModel.state.value
        assertTrue(state.showLogoutDialog)
    }

    //Цикл жизни диалогового окна
    @Test
    fun `should hide logout confirmation dialog`() {
        // сначала показываем диалог
        viewModel.showLogoutDialog()
        assertTrue(viewModel.state.value.showLogoutDialog)

        // скрываем
        viewModel.hideLogoutDialog()

        // диалог должен исчезнуть
        val state = viewModel.state.value
        assertFalse(state.showLogoutDialog)
    }


}