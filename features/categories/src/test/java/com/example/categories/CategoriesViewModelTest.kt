package com.example.categories

import com.example.categories.di.CategoriesComponent
import com.example.domain.entities.CategoryDomainModel
import com.example.domain.interactors.CategoriesInteractor
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CategoriesViewModelTest {

    private lateinit var viewModel: CategoriesViewModel
    private lateinit var interactor: CategoriesInteractor
    private lateinit var component: CategoriesComponent
    private val testDispatcher = StandardTestDispatcher()

    private val fakeDB = listOf(
        CategoryDomainModel(
            id = "1",
            name_en = "animals",
            name = "Животные",
            image = "image1"
        ),
        CategoryDomainModel(
            id = "2",
            name_en = "adults",
            name = "Взрослые",
            image = "image2"
        ),
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        interactor = mockk()
        component = mockk {
            every { inject(any()) } answers {
                val viewModel = arg<CategoriesViewModel>(0)
                viewModel.interactor = interactor
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load categories from db`() {
        runBlocking {
            coEvery { interactor.getCategories() } returns fakeDB

            viewModel = CategoriesViewModel(component)
            testDispatcher.scheduler.advanceUntilIdle()

            val state = viewModel.screenState.value
            assertTrue(state is CategoriesScreenState.Done)
            assertEquals(fakeDB, (state as CategoriesScreenState.Done).categoryList)
            coVerify { interactor.getCategories() }
        }
    }

    @Test
    fun `loadCategoriesListFromDb should emit Progress then Done with categories`() {
        runBlocking {
            coEvery { interactor.getCategories() } returns fakeDB
            viewModel = CategoriesViewModel(component)

            viewModel.loadCategoriesListFromDb()
            testDispatcher.scheduler.advanceUntilIdle()

            val state = viewModel.screenState.value
            assertTrue(state is CategoriesScreenState.Done)
            assertEquals(fakeDB, (state as CategoriesScreenState.Done).categoryList)
            coVerify(exactly = 2) { interactor.getCategories() }
        }
    }
}