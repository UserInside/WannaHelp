package com.example.wannahelp.newsScreen.newsFilterScreen

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wannahelp.R
import com.example.wannahelp.common.Category
import com.example.wannahelp.common.datastore
import com.example.wannahelp.newsScreen.NewsViewModel.Companion.CHOSEN_CATEGORIES
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsFilterViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val ctx = application.applicationContext

    private val initialFilterList = getInitialFilterList(application).toList()

    val setOfChosenCategories = getSelectedCategoriesSet().filterNotNull().stateIn(
        viewModelScope, SharingStarted.Eagerly, initialFilterList.map { it.category.name }.toSet()
    )

    val listOfCategoryFiltersToShow = MutableStateFlow<List<FilterCategoryCard>>(emptyList())

    private var tmpSetOfFilteredCategoriesToSave = mutableSetOf<String>()

    init {
        viewModelScope.launch {
            getSelectedCategoriesSet().first() //почему без этого не работает? потому что эмитит элемент?
            tmpSetOfFilteredCategoriesToSave.clear()
            tmpSetOfFilteredCategoriesToSave.addAll(setOfChosenCategories.value)

            updateLTS()
        }
    }

    private fun updateLTS() {
        listOfCategoryFiltersToShow.value = initialFilterList.map { item ->
                if (setOfChosenCategories.value.contains(item.category.toString()) == true) {
                    item.copy(isChecked = true)
                } else {
                    item.copy(isChecked = false)
                }
            }
    }

    fun getSelectedCategoriesSet(): Flow<Set<String>?> = ctx.datastore.data.map { preference ->
        preference[stringSetPreferencesKey(CHOSEN_CATEGORIES)]
    }

    suspend fun saveChosenCategories() {
        try {
            ctx.datastore.edit { prefs ->
                prefs[stringSetPreferencesKey(CHOSEN_CATEGORIES)] = tmpSetOfFilteredCategoriesToSave
            }
        } catch (e: Exception) {
            Log.e("DSTORE", "Save failed", e)
        }
    }

    fun addNewsItemToFilter(category: String) =
        tmpSetOfFilteredCategoriesToSave.add(category)


    fun removeNewsItemFromFilter(category: String) =
        tmpSetOfFilteredCategoriesToSave.remove(category)

    private fun getInitialFilterList(application: Application) = mutableListOf(
        FilterCategoryCard(
            application.resources.getString(R.string.tv_cat_kids),
            Category.KIDS,
        ),
        FilterCategoryCard(
            application.resources.getString(R.string.tv_cat_adults),
            Category.ADULTS,
        ),
        FilterCategoryCard(
            application.resources.getString(R.string.tv_cat_events),
            Category.EVENTS,
        ),
        FilterCategoryCard(
            application.resources.getString(R.string.tv_cat_aged),
            Category.AGED,
        ),
        FilterCategoryCard(
            application.resources.getString(R.string.tv_cat_animals),
            Category.ANIMALS,
        ),
    )
}
