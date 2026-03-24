package com.example.userprofile

import com.example.userprofile.model.domain.UserProfile
import com.example.userprofile.model.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MockUserRepository : UserRepository {

    private val _userProfile = MutableStateFlow(
        UserProfile(
            id = "1",
            name = "Test User",
            email = "test@example.com"
        )
    )

    override fun getUserProfile(): Flow<UserProfile> = _userProfile.asStateFlow()

    override suspend fun updateUserProfile(profile: UserProfile) {
        _userProfile.value = profile
    }

    override suspend fun logout() {
        _userProfile.value = UserProfile()
    }

    fun setUserProfile(profile: UserProfile) {
        _userProfile.value = profile
    }

    var delayMs: Long = 0L

}