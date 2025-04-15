package com.example.wannahelp.newsScreen

import com.example.wannahelp.common.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsItem(
    val id: Int = 0,
    @SerialName("image")
    val imageRes: String = "",
    val name: String = "",
    val description: String = "",
    val date: String = "",
    val category: Category = Category.KIDS,
) : java.io.Serializable
