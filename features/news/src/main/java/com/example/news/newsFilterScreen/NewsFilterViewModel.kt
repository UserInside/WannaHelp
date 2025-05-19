package com.example.news.newsFilterScreen

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.extensions.datastore
import com.example.news.NewsViewModel
import com.example.news.newsFilterScreen.newsFilterRecycler.FilterCategoryCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NewsFilterViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val ctx = application.applicationContext

    private val initialFilterList = FilterCategoryCard.Companion.getFilterCategoryCardsList(application)

    private var setOfChosenCategories: Set<String>? = emptySet()

    val listOfCategoryFiltersToShow =
        MutableStateFlow<List<FilterCategoryCard>>(emptyList())

    private var tmpSetOfFilteredCategoriesToSave = mutableSetOf<String>()

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

    private fun updateLTS() {
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
        return cachedSetOfCategories ?: ctx.datastore.data.map { preference ->
            preference[stringSetPreferencesKey(NewsViewModel.Companion.CHOSEN_CATEGORIES)]
                ?: initialFilterList.map { it.category.name }.toSet()
        }.first().also { cachedSetOfCategories = it }
    }

    suspend fun saveChosenCategories() {
        try {
            ctx.datastore.edit { prefs ->
                prefs[stringSetPreferencesKey(NewsViewModel.Companion.CHOSEN_CATEGORIES)] = tmpSetOfFilteredCategoriesToSave
            }
        } catch (e: Exception) {
            Log.e("DSTORE", "Save failed", e)
        }
    }

    fun addNewsItemToFilter(category: String) = tmpSetOfFilteredCategoriesToSave.add(category)

    fun removeNewsItemFromFilter(category: String) = tmpSetOfFilteredCategoriesToSave.remove(category)
}
