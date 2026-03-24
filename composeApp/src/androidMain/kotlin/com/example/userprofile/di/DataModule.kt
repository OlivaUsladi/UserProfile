package com.example.userprofile.di

import com.example.userprofile.data.repository.SettingsRepositoryImpl
import com.example.userprofile.data.repository.UserRepositoryImpl
import com.example.userprofile.model.repository.SettingsRepository
import com.example.userprofile.model.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataAppModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl(androidContext()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
}