package com.example.wannahelp.newsScreen.newsFilterScreen

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

class DataStoreManager(val context: Context) {

    suspend fun saveSelectedCategoriesSet(set: Set<String>) {
        Log.i("DSTORE", "dsm try save set $set")
        Log.i("DSTORE", "stored? ${isCatStored().first()} ")
        try {
            // Важная операция, которая должна завершиться
            withContext(NonCancellable) {
                context.datastore.edit { prefs ->
                    prefs[stringSetPreferencesKey(CHOSEN_CATEGORIES)] = set
                }
            }
            Log.i("DSTORE", "Successfully saved (even after cancellation)")
        } catch (e: Exception) {
            Log.e("DSTORE", "Save failed", e)
        }
        Log.i("DSTORE", "after save stored? ${isCatStored().first()}")
        Log.i("DSTORE", "after save stored? ${getFCKNDS()}")
    }

    fun getSelectedCategoriesSet(): Flow<Set<String>?> {
        Log.i("DSTORE", "try get set from by dsm")

        return context.datastore.data.map { preference ->
            preference[stringSetPreferencesKey(CHOSEN_CATEGORIES)]
        }
    }

    fun isCatStored(): Flow<Boolean> =
        context.datastore.data.map { preference ->
            preference.contains(stringSetPreferencesKey(CHOSEN_CATEGORIES))
        }

    suspend fun getFCKNDS(): Set<String>? {
        return context.datastore.data.map { preference ->
            preference[stringSetPreferencesKey(CHOSEN_CATEGORIES)]
        }.first()
    }
}

class NewsFilterViewModel(application: Application) : AndroidViewModel(application) {

    override fun onCleared() {
        Log.i("DSTORE", "onCleared ${setOfChosenCategories.value}")
        super.onCleared()
    }

    val dsm = DataStoreManager(application)

    val setOfChosenCategories = dsm.getSelectedCategoriesSet().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), null
    )

    val listOfCategoryFiltersToShow =
        MutableStateFlow(getInitialFilterList(application).map { item ->
            Log.i("DSTORE", "${setOfChosenCategories.value}")
            Log.i(
                "DSTORE",
                "filter ${item.title} ${setOfChosenCategories.value?.contains(item.category.toString()) == true}"
            )
            if (setOfChosenCategories.value?.contains(item.category.toString()) == true) {
                item.copy(isChecked = true)
            } else {
                item.copy(isChecked = false)
            }
        })

    private val tmpSetOfFilteredCategoriesToSave = mutableSetOf<String>()

    init{
        Log.i("DSTORE", "--- onInit FilterScreen ${setOfChosenCategories.value}")
        viewModelScope.launch {
            setOfChosenCategories.collect { categories ->
                tmpSetOfFilteredCategoriesToSave.clear()
                categories?.let {
                    tmpSetOfFilteredCategoriesToSave.addAll(it)
                }
            }
        }
    }

    suspend fun saveChosenCategories() {
        dsm.saveSelectedCategoriesSet(tmpSetOfFilteredCategoriesToSave)
        Log.i("DSTORE", "saved categories -> $tmpSetOfFilteredCategoriesToSave ")
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
