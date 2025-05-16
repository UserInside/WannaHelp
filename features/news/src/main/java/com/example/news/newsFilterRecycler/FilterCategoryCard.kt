package com.example.news.newsFilterRecycler

import android.app.Application
import com.example.domain.entities.Category
import com.example.common.R as commonR

data class FilterCategoryCard(
    val title: String,
    val category: Category,
    var isChecked: Boolean = true,
) {
    companion object {
        fun getFilterCategoryCardsList(application: Application) =
            listOf(
                FilterCategoryCard(
                    application.resources.getString(commonR.string.tv_cat_kids),
                    Category.KIDS,
                ),
                FilterCategoryCard(
                    application.resources.getString(commonR.string.tv_cat_adults),
                    Category.ADULTS,
                ),
                FilterCategoryCard(
                    application.resources.getString(commonR.string.tv_cat_aged),
                    Category.AGED,
                ),
                FilterCategoryCard(
                    application.resources.getString(commonR.string.tv_cat_events),
                    Category.EVENTS,
                ),
                FilterCategoryCard(
                    application.resources.getString(commonR.string.tv_cat_animals),
                    Category.ANIMALS,
                ),
            )
    }
}
