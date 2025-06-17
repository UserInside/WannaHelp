package com.example.news.newsFilterScreen

import com.example.data.storage.StorageProvider
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class NewsFilterViewModelTest {

    private lateinit var viewModel: NewsFilterViewModel

    @RelaxedMockK
    val storageProvider : StorageProvider = mockk()


    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addNewsItemToFilter adds category to tmpSet`() {
        viewModel = NewsFilterViewModel(storageProvider)
        val fakeCategory = "Adults"

        viewModel.addNewsItemToFilter(fakeCategory)

        assertTrue(viewModel.tmpSetOfFilteredCategoriesToSave.contains(fakeCategory))
    }

    @Test
    fun `removeNewsItemFromFilter removes category from tmpSet`() {
        viewModel = NewsFilterViewModel(storageProvider)
        val fakeCategory = "Aged"
        assertFalse(viewModel.tmpSetOfFilteredCategoriesToSave.contains(fakeCategory))

        viewModel.addNewsItemToFilter(fakeCategory)

        viewModel.removeNewsItemFromFilter(fakeCategory)

        assertFalse(viewModel.tmpSetOfFilteredCategoriesToSave.contains(fakeCategory))
    }

    @Test
    fun `updateLTS updates list with chosen categories`() {
        viewModel = NewsFilterViewModel(storageProvider)
        val fakeCategories = setOf("Kids", "Animals")
        viewModel.setOfChosenCategories = fakeCategories

        viewModel.updateLTS()

        viewModel.listOfCategoryFiltersToShow.value.forEach { item ->
            if (fakeCategories.contains(item.category.toString())) {
                assertTrue(item.isChecked)
            } else {
                assertFalse(item.isChecked)
            }
        }
    }
}