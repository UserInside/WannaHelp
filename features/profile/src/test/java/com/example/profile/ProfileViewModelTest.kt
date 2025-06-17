package com.example.profile

import com.example.data.network.ApiService
import com.example.domain.entities.FriendCardDomainModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProfileViewModelTest {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var apiService: ApiService
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        apiService = mockk()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load friends from api`() {
        val fakeApiResult = listOf<FriendCardDomainModel>(
            FriendCardDomainModel(
                id = 1,
                image = "image1",
                name = "name1",
            ),
            FriendCardDomainModel(
                id = 2,
                image = "image2",
                name = "name2",
            )
        )

        coEvery { apiService.getFriends() } returns fakeApiResult

        viewModel = ProfileViewModel(apiService)
        testDispatcher.scheduler.advanceUntilIdle()

        val actual = viewModel.friendsList.value
        val expected = fakeApiResult.map { FriendCardMapper.mapFriendsFromDomainToUi(it) }

        assertEquals(expected, actual)
        coVerify(exactly = 1) { apiService.getFriends() }
    }
}