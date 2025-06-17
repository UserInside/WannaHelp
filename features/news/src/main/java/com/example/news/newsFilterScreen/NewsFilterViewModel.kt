package com.example.news.newsFilterScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.storage.StorageProvider
import com.example.news.NewsViewModel
import com.example.news.di.NewsComponent
import com.example.news.newsFilterScreen.newsFilterRecycler.FilterCategoryCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFilterViewModelFactory(
    newsComponent: NewsComponent
) : ViewModelProvider.Factory {

    @Inject
    lateinit var storageProvider: StorageProvider

    init {
        newsComponent.inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsFilterViewModel(storageProvider) as T
    }
}

class NewsFilterViewModel @Inject constructor(
    private val storageProvider: StorageProvider
) : ViewModel() {
    @SuppressLint("StaticFieldLeak")

    private val initialFilterList =
        FilterCategoryCard.Companion.getFilterCategoryCardsList()

    var setOfChosenCategories: Set<String>? = emptySet()

    val listOfCategoryFiltersToShow =
        MutableStateFlow<List<FilterCategoryCard>>(emptyList())

    var tmpSetOfFilteredCategoriesToSave = mutableSetOf<String>()

    init {
        viewModelScope.launch {
            setOfChosenCategories = getChosenCategoriesFromDS()

            updateLTS()

            tmpSetOfFilteredCategoriesToSave.clear()
            setOfChosenCategories?.let {
                tmpSetOfFilteredCategoriesToSave.addAll(it)
            }
        }
    }

    private var cachedSetOfCategories: Set<String>? = emptySet<String>()

    fun updateLTS() {
        listOfCategoryFiltersToShow.value =
            initialFilterList
                .map { item ->
                    if (setOfChosenCategories?.contains(item.category.toString()) == true) {
                        item.copy(isChecked = true)
                    } else {
                        item.copy(isChecked = false)
                    }
                }
    }

    private suspend fun getChosenCategoriesFromDS(): Set<String>? {
        return cachedSetOfCategories ?: storageProvider.data.map { preference ->
            preference[stringSetPreferencesKey(NewsViewModel.Companion.CHOSEN_CATEGORIES)]
                ?: initialFilterList.map { it.category.name }.toSet()
        }.first().also {
            Log.e("VMV", "$it") //tyyt
            cachedSetOfCategories = it
        }
    }

    suspend fun saveChosenCategories() {
        try {
            storageProvider.updateData { prefs ->
                prefs[stringSetPreferencesKey(NewsViewModel.Companion.CHOSEN_CATEGORIES)] =
                    tmpSetOfFilteredCategoriesToSave
            }
        } catch (e: Exception) {
            Log.e("DSTORE", "Save failed", e)
        }
    }

    fun addNewsItemToFilter(category: String) = tmpSetOfFilteredCategoriesToSave.add(category)

    fun removeNewsItemFromFilter(category: String) =
        tmpSetOfFilteredCategoriesToSave.remove(category)
}
