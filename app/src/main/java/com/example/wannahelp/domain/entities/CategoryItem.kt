package com.example.wannahelp.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class CategoryItem(
    val id: String = "",
    val name_en: String = "",
    val name: String = "",
    val image: String = "",
) : java.io.Serializable