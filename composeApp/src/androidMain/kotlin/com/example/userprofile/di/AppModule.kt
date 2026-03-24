package com.example.userprofile.di

import com.example.userprofile.feature.ProfileViewModel
import com.example.userprofile.feature.domain.LoadProfileUseCase
import com.example.userprofile.feature.domain.SaveSettingsUseCase
import com.example.userprofile.feature.domain.ValidateNameUseCase
import com.example.userprofile.model.repository.SettingsRepository
import com.example.userprofile.model.repository.UserRepository
//import org.koin.core.module.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::ProfileViewModel)
}