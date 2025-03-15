package com.example.wannahelp.wannaHelpScreen

import com.example.wannahelp.common.Category
import kotlinx.serialization.Serializable

@Serializable
data class CategoryItem(
    val id: Int = 0,
    val image: String = "",
    val title: String = "",
    val category: Category = Category.KIDS,
)
