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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsFilterViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val ctx = application.applicationContext

    override fun onCleared() {
        Log.i("DSTORE", "onCleared cats listToShow ${listOfCategoryFiltersToShow.value}")
        super.onCleared()
    }

    private val initialFilterList = getInitialFilterList(application).toList()

    val setOfChosenCategories = getSelectedCategoriesSet().stateIn(
        viewModelScope, SharingStarted.Eagerly, null
    )

    val listOfCategoryFiltersToShow =
        MutableStateFlow<List<FilterCategoryCard>>(emptyList())

    private var tmpSetOfFilteredCategoriesToSave = mutableSetOf<String>()

    init {
        viewModelScope.launch {
            Log.i("DSTORE", "--1- onInit FilterScreen set ${setOfChosenCategories.value}")
            Log.i("DSTORE", "--2- onInit FilterScreen ${getSelectedCategoriesSet().first()}")
            Log.i("DSTORE", "--3- onInit FilterScreen set ${setOfChosenCategories.value}")

            tmpSetOfFilteredCategoriesToSave.clear()
            setOfChosenCategories.value?.let {
                tmpSetOfFilteredCategoriesToSave.addAll(it)
            }
            updateLTS()
        }
    }

    private fun updateLTS() {
        Log.i("DSTORE", "-- upd LTS set ${setOfChosenCategories.value}")

        listOfCategoryFiltersToShow.value = initialFilterList
            .map { item ->
                if (setOfChosenCategories.value?.contains(item.category.toString()) == true) {
                    item.copy(isChecked = true)
                } else {
                    item.copy(isChecked = false)
                }
            }
    }

    fun getSelectedCategoriesSet(): Flow<Set<String>?> {
        Log.i("DSTORE", "g- try get set from ds")
        return ctx.datastore.data.map { preference ->
            preference[stringSetPreferencesKey(CHOSEN_CATEGORIES)] ?: initialFilterList.map { it.category.name }.toSet()
        }.also {
            Log.i("DSTORE", "g- loaded from ds")
        }
    }

    suspend fun saveChosenCategories() {
        Log.i("DSTORE", "try save set to ds $tmpSetOfFilteredCategoriesToSave")
        try {
            ctx.datastore.edit { prefs ->
                prefs[stringSetPreferencesKey(CHOSEN_CATEGORIES)] = tmpSetOfFilteredCategoriesToSave
            }
            Log.i("DSTORE", "Successfully saved (even after cancellation)")
        } catch (e: Exception) {
            Log.e("DSTORE", "Save failed", e)
        }
    }

    fun addNewsItemToFilter(category: String) {
        Log.i("DSTORE", "add tmpSet ${setOfChosenCategories.value} ")
        tmpSetOfFilteredCategoriesToSave.add(category)
    }

    fun removeNewsItemFromFilter(category: String) =
        tmpSetOfFilteredCategoriesToSave.remove(category)

    private fun getInitialFilterList(application: Application) =
        mutableListOf(
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
