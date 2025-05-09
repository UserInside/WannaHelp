package com.example.wannahelp.presentation.newsScreen.newsFilterScreen.newsFiletrRecycler

import android.app.Application
import com.example.wannahelp.R
import com.example.domain.entities.Category

data class FilterCategoryCard(
    val title: String,
    val category: Category,
    var isChecked: Boolean = true,
) {
    companion object {
        fun getFilterCategoryCardsList(application: Application) =
            listOf(
                FilterCategoryCard(
                    application.resources.getString(R.string.tv_cat_kids),
                    Category.KIDS,
                ),
                FilterCategoryCard(
                    application.resources.getString(R.string.tv_cat_adults),
                    Category.ADULTS,
                ),
                FilterCategoryCard(
                    application.resources.getString(R.string.tv_cat_aged),
                    Category.AGED,
                ),
                FilterCategoryCard(
                    application.resources.getString(R.string.tv_cat_events),
                    Category.EVENTS,
                ),
                FilterCategoryCard(
                    application.resources.getString(R.string.tv_cat_animals),
                    Category.ANIMALS,
                ),
            )
    }
}
