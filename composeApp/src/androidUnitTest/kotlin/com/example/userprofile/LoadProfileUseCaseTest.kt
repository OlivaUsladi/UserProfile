package com.example.userprofile

import com.example.userprofile.MockUserRepository
import com.example.userprofile.feature.domain.LoadProfileUseCase
import com.example.userprofile.model.domain.UserProfile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LoadProfileUseCaseTest {

    @Test
    fun `should load user profile from repository`() = runTest {
        val mockRepository = MockUserRepository()
        val expectedProfile = UserProfile(
            id = "1",
            name = "Test User",
            email = "test@example.com"
        )
        //mockRepository.setUserProfile(expectedProfile)

        val loadProfileUseCase = LoadProfileUseCase(mockRepository)

        val result = loadProfileUseCase().first()

        assertEquals(expectedProfile.id, result.id)
        assertEquals(expectedProfile.name, result.name)
        assertEquals(expectedProfile.email, result.email)
    }

}