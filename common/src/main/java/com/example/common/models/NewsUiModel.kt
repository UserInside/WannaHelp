package com.example.common.models

import com.example.domain.entities.Category
import kotlinx.serialization.Serializable

@Serializable
data class NewsUiModel(
    val id: Int = 0,
    val imageRes: String = "",
    val name: String = "",
    val description: String = "",
    val date: String = "",
    val isRead: Boolean = false,
    val category: Category = Category.KIDS,
): java.io.Serializable

