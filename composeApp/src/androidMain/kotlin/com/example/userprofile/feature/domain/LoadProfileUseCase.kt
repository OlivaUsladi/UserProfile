package com.example.userprofile.feature.domain

import com.example.userprofile.model.domain.UserProfile
import com.example.userprofile.model.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class LoadProfileUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<UserProfile> = userRepository.getUserProfile()
}