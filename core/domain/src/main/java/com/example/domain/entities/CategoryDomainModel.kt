package com.example.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDomainModel(
    val id: String = "",
    val name_en: String = "",
    val name: String = "",
    val image: String = "",
) : java.io.Serializable
