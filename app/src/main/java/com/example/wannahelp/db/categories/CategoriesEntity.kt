package com.example.wannahelp.db.categories

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoriesEntity(
    @PrimaryKey()
    val id: Int,
    val nameEn: String,
    val name: String,
    val image: String,
)