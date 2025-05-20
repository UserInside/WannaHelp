package com.example.domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsDomainModel(
    val id: Int = 0,
    @SerialName("image")
    val imageRes: String = "",
    val name: String = "",
    val description: String = "",
    val date: String = "",
    val isRead: Boolean = false,
    val category: Category = Category.KIDS,
) : java.io.Serializable
