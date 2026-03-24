package com.example.userprofile.di

import com.example.userprofile.feature.domain.LoadProfileUseCase
import com.example.userprofile.feature.domain.SaveSettingsUseCase
import com.example.userprofile.feature.domain.ValidateNameUseCase
import org.koin.dsl.module

val domainModule = module {
    single { LoadProfileUseCase(get()) }
    single { SaveSettingsUseCase(get()) }
    single { ValidateNameUseCase() }

}