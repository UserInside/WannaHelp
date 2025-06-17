package com.example.news.newsFilterScreen.newsFilterRecycler

import com.example.domain.entities.Category

data class FilterCategoryCard(
    val title: String,
    val category: Category,
    var isChecked: Boolean = true,
) {
    companion object {
        fun getFilterCategoryCardsList(): List<FilterCategoryCard> {

            return listOf(
                FilterCategoryCard(
//                    application.resources.getString(commonR.string.tv_cat_kids),
                    "Дети",
                    Category.KIDS,
                ),
                FilterCategoryCard(
//                    application.resources.getString(commonR.string.tv_cat_adults),
                    "Взрослые",
                    Category.ADULTS,
                ),
                FilterCategoryCard(
//                    application.resources.getString(commonR.string.tv_cat_aged),
                    "Старые",
                    Category.AGED,
                ),
                FilterCategoryCard(
//                    application.resources.getString(commonR.string.tv_cat_events),
                    "Мероприятия",
                    Category.EVENTS,
                ),
                FilterCategoryCard(
//                    application.resources.getString(commonR.string.tv_cat_animals),
                    "Животные",
                    Category.ANIMALS,
                ),
            )
        }
    }
}
