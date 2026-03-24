package com.example.userprofile.model.repository

import com.example.userprofile.model.domain.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserProfile(): Flow<UserProfile>
    suspend fun updateUserProfile(profile: UserProfile)
    suspend fun logout()
}