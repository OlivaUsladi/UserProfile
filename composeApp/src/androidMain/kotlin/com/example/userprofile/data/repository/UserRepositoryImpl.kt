package com.example.userprofile.data.repository

import com.example.userprofile.model.domain.UserProfile
import com.example.userprofile.model.repository.UserRepository
import com.example.userprofile.model.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val settingsRepository: SettingsRepository
) : UserRepository {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)

    override fun getUserProfile(): Flow<UserProfile> {
        return settingsRepository.getUserProfile().map { dataStoreProfile ->

            if (dataStoreProfile.name.isNotBlank()) {
                _userProfile.value = dataStoreProfile
                dataStoreProfile
            } else {

                val defaultProfile = UserProfile(
                    id = "1",
                    name = "",
                    email = ""
                )
                _userProfile.value = null
                defaultProfile
            }
        }
    }

    override suspend fun updateUserProfile(profile: UserProfile) {

        _userProfile.value = profile

        settingsRepository.saveUserProfile(profile)
    }

    override suspend fun logout() {
        val emptyProfile = UserProfile()
        _userProfile.value = emptyProfile
        settingsRepository.saveUserProfile(emptyProfile)
    }
}