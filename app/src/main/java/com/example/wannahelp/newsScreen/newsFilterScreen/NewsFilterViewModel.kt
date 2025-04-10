package com.example.wannahelp.newsScreen.newsFilterScreen

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.example.wannahelp.R
import com.example.wannahelp.common.Category
import com.example.wannahelp.newsScreen.NewsViewModel.Companion.CHOSEN_CATEGORIES_KEY
import kotlinx.coroutines.flow.MutableStateFlow

class NewsFilterViewModel(application: Application) : AndroidViewModel(application) {
    private var sharedPref: SharedPreferences =
        application.applicationContext.getSharedPreferences(CHOSEN_CATEGORIES_KEY, MODE_PRIVATE)

    private var setOfChosenCategories =
        sharedPref.getStringSet(CHOSEN_CATEGORIES_KEY, null) ?: Category.entries.map { it.name }
            .toSet()

    val listOfCategoryFiltersToShow =
        MutableStateFlow<List<FilterCategoryCard>>(
            getInitialFilterList(application).map { item ->
                if (setOfChosenCategories.contains(item.category.toString())) {
                    item.copy(isChecked = true)
                } else {
                    item.copy(isChecked = false)
                }
            },
        )

    private val tmpSetOfFilteredCategoriesToSave = setOfChosenCategories.toMutableSet()

    fun addNewsItemToFilter(category: String) = tmpSetOfFilteredCategoriesToSave.add(category)

    fun removeNewsItemFromFilter(category: String) = tmpSetOfFilteredCategoriesToSave.remove(category)

    fun saveChosenCategories() {
        setOfChosenCategories = tmpSetOfFilteredCategoriesToSave.toSet()
        sharedPref.edit().apply {
            putStringSet(CHOSEN_CATEGORIES_KEY, tmpSetOfFilteredCategoriesToSave)
            apply()
        }
    }

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
