package com.example.wannahelp.presentation.newsScreen.newsFilterScreen

import com.example.wannahelp.common.Category

data class FilterCategoryCard(
    val title: String,
    val category: Category,
    var isChecked: Boolean = true,
)
