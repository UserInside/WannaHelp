package com.example.data.db.categories

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "categories")
data class CategoriesEntity(
    @PrimaryKey()
    val id: Int,
    val nameEn: String,
    val name: String,
    val image: String,
)
